package com.qianmi.controller;

import com.qianmi.weixin.MessageHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import qq.weixin.mp.aes.WXBizMsgCrypt;

import java.util.Arrays;

/**
 * 微信验证
 * Created by li on 15/8/21.
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController extends BaseController {

    @Value("${weixin.token}")
    private String token;

    @Value("${weixin.encoding.aes_key}")
    private String encodingAesKey;

    @Value("${weixin.appid}")
    private String appId;

    @Autowired
    private MessageHandler messageHandler;

    @RequestMapping(method = RequestMethod.GET)
    public String checkSignature(String signature, String timestamp, String nonce, String echostr) {
        logger.debug("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
        String[] str = {token, timestamp, nonce};
        Arrays.sort(str);

        String bigStr = str[0] + str[1] + str[2];

        String digest = DigestUtils.sha1Hex(bigStr.getBytes());

        if (StringUtils.equals(digest, signature)) {
            System.out.println("availability");
            return echostr;
        }
        System.out.println("invalid");
        return "";
    }

    /**
     * 接受微信消息(安全请求)
     *
     * *****异常:java.security.InvalidKeyException: Illegal key size*****
     * *****警告:上述aes key size异常请下载oracle jce覆盖本地jdk或jre文件*****
     *
     * @param encryptType  加密类型
     * @param msgSignature 签名串,即:对消息体的签名
     * @param timestamp    时间戳
     * @param nonce        随机串
     * @param data         密文
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String receive(@RequestParam("encrypt_type") String encryptType,
                   @RequestParam("msg_signature") String msgSignature,
                   @RequestParam("timestamp") String timestamp,
                   @RequestParam("nonce") String nonce,
                   @RequestBody String data) {
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            String msg = pc.decryptMsg(msgSignature, timestamp, nonce, data);
            logger.debug("msg:{}", msg);
            messageHandler.execute(msg);
        } catch (Exception e) {
            logger.error("weixin error", e);
        }
        return "";
    }
}

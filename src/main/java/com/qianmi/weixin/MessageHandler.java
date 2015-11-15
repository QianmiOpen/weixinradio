package com.qianmi.weixin;

import com.qianmi.AppConstants;
import com.qianmi.domain.User;
import com.qianmi.service.UserService;
import com.qianmi.weixin.exception.UnknownEventException;
import com.qianmi.weixin.exception.UnknownRequestMsgTypeException;
import com.qianmi.weixin.exception.WeixinException;
import com.qianmi.weixin.message.MessageBase;
import com.qianmi.weixin.message.request.*;
import com.qianmi.weixin.message.request.event.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息处理类
 */
@Component
public class MessageHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate template;

    public void execute(String msg) throws Exception {
        RequestMsgType msgType = MsgTypeHelper.getRequestMsgType(msg);
        switch (msgType) {
            case text:
                onTextRequest(new RequestMessageText(), msg);
                break;
            case image:
                new RequestMessageImage();
                break;
            case voice:
                new RequestMessageVoice();
                break;
            case video:
                new RequestMessageVideo();
                break;
            case shortvideo:
                new RequestMessageShortvideo();
                break;
            case location:
                new RequestMessageLocation();
                break;
            case link:
                new RequestMessageLink();
                break;
            case event:
                EventType eventType = EventHelper.getEventType(msg);
                switch (eventType) {
                    case subscribe: // 关注
                        onMessageEvent_Subscribe(new RequestMessageEvent_Subscribe(), msg);
                        break;
                    case unsubscribe:
                        new RequestMessageEvent_Unsubscribe();
                        break;
                    case SCAN:      // 扫码
                        onMessageEvent_Scan(new RequestMessageEvent_Scan(), msg);
                        break;
                    case LOCATION:
                        new RequestMessageEvent_Location();
                        break;
                    case CLICK:
                        new RequestMessageEvent_Click();
                        break;
                    case VIEW:
                        new RequestMessageEvent_View();
                        break;
                    default:
                        logger.warn("UnknownEventException, msg:{}", msg);
//                        throw new UnknownEventException();
                }
                break;
            default:
                logger.warn("UnknownRequestMsgTypeException, msg:{}", msg);
//                throw new UnknownRequestMsgTypeException();
        }
    }

    protected void onTextRequest(MessageBase messageBase, String msg) throws WeixinException {
        RequestMessageText requestMessageText = (RequestMessageText) FillEntityXml.convertXmlToEntity(msg, messageBase);
    }

    /**
     * 用户扫码关注
     * @param messageBase
     * @param msg
     * @throws WeixinException
     */
    protected void onMessageEvent_Subscribe(MessageBase messageBase, String msg) throws WeixinException {
        RequestMessageEvent_Subscribe subscribe = (RequestMessageEvent_Subscribe) FillEntityXml.convertXmlToEntity(msg, messageBase);
        logger.debug("messageEvent_Subscribe: {}", subscribe);
        String eventKey = subscribe.getEventKey();
        // workNo
        eventKey = AppConstants.WORK_NO_PREFIX + StringUtils.substringAfter(eventKey, "qrscene_");
        logger.debug("eventKey: {}", eventKey);
        User user = userService.get(eventKey);
        logger.debug("user: {}", user);
        // openid
        user.setWxOpenId(subscribe.getFromUserName());
        userService.save(user);

        // Send webSocket
        this.template.convertAndSend("/topic/message", eventKey);
    }

    protected void onMessageEvent_Scan(MessageBase messageBase, String msg) throws WeixinException {
        RequestMessageEvent_Scan scan = (RequestMessageEvent_Scan) FillEntityXml.convertXmlToEntity(msg, messageBase);
        logger.debug("MessageEvent_Scan: {}", scan);
        // workNo
        String eventKey = AppConstants.WORK_NO_PREFIX + scan.getEventKey();
        logger.debug("userService: {}", userService);
        User user = userService.get(eventKey);
        // openid
        user.setWxOpenId(scan.getFromUserName());
        userService.save(user);
        logger.debug("simpleTemplate: {}", template);
        // Send webSocket
        this.template.convertAndSend("/topic/message", eventKey);
    }

}

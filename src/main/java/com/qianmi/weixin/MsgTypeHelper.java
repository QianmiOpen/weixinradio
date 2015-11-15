package com.qianmi.weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qianmi.weixin.message.request.RequestMsgType;

/**
 * 消息类型获取
 */
public class MsgTypeHelper {

    public static RequestMsgType getRequestMsgType(String msg) {
        try {
            JsonNode json = new XmlMapper().readTree(msg);
            JsonNode msgType = json.get("MsgType");
            return RequestMsgType.valueOf(msgType.asText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RequestMsgType._UNKNOWN;
    }

}

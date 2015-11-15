package com.qianmi.weixin.message.request.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.qianmi.weixin.message.MessageBase;
import com.qianmi.weixin.message.request.RequestMsgType;

/**
 * 用户发送事件消息基类
 */
@ToString(callSuper = true)
public class RequestMessageEventBase extends MessageBase {

    @JacksonXmlCData
    @Getter(AccessLevel.PUBLIC)
    private RequestMsgType MsgType = RequestMsgType.event;

    @JacksonXmlCData
    @Getter(AccessLevel.PUBLIC)
    private EventType Event;

}

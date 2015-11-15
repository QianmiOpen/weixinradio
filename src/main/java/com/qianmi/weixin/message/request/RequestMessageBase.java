package com.qianmi.weixin.message.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.qianmi.weixin.message.MessageBase;

/**
 * 用户发送消息基类
 */
@ToString(callSuper = true)
public class RequestMessageBase extends MessageBase {

    @JacksonXmlCData
    @Getter(AccessLevel.PUBLIC)
    private RequestMsgType MsgType;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private long MsgId;

}

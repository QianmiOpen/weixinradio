package com.qianmi.weixin.message.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.qianmi.weixin.message.MessageBase;

/**
 * 回复消息基类
 */
@ToString(callSuper = true)
public class ResponseMessageBase extends MessageBase {

    @JacksonXmlCData
    @Getter(AccessLevel.PUBLIC)
    private ResponseMsgType MsgType;

}

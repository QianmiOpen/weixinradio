package com.qianmi.weixin.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 文本消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageText extends RequestMessageBase {

    @Override
    public RequestMsgType getMsgType() {
        return RequestMsgType.text;
    }

    /**
     * 文本消息内容
     */
    @JacksonXmlCData
    private String Content;

}

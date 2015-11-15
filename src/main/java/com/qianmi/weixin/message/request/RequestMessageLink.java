package com.qianmi.weixin.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 链接消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageLink extends RequestMessageBase {

    @Override
    public RequestMsgType getMsgType() {
        return RequestMsgType.link;
    }

    /**
     * 消息标题
     */
    @JacksonXmlCData
    private String Title;
    /**
     * 消息描述
     */
    @JacksonXmlCData
    private String Description;
    /**
     * 消息链接
     */
    @JacksonXmlCData
    private String Url;
}

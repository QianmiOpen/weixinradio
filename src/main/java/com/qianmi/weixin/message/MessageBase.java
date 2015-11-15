package com.qianmi.weixin.message;

import lombok.Data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;

/**
 * 所有消息基类
 */
@Data public class MessageBase {
    @JacksonXmlCData
    private String ToUserName;

    @JacksonXmlCData
    private String FromUserName;

    @JacksonXmlCData
    private String CreateTime;
}

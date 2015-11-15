package com.qianmi.weixin.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 回复图片消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class ResponseMessageImage extends ResponseMessageBase {

    @Override
    public ResponseMsgType getMsgType() {
        return ResponseMsgType.image;
    }

    private Image image;

    @Data class Image {
        @JacksonXmlCData
        public String MediaId;
    }

}


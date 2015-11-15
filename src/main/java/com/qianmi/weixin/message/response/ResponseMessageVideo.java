package com.qianmi.weixin.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 回复视频消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class ResponseMessageVideo extends ResponseMessageBase {

    @Override
    public ResponseMsgType getMsgType() {
        return ResponseMsgType.video;
    }

    private Video video;

    @Data class Video {
        @JacksonXmlCData
        private String MediaId;

        @JacksonXmlCData
        private String Title;

        @JacksonXmlCData
        private String Description;
    }

}

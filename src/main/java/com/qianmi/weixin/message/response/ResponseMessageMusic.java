package com.qianmi.weixin.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 回复音乐消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class ResponseMessageMusic extends ResponseMessageBase {

    @Override
    public ResponseMsgType getMsgType() {
        return ResponseMsgType.music;
    }

    private Music music;

    @Data class Music {
        @JacksonXmlCData
        private String Title;

        @JacksonXmlCData
        private String Description;

        @JacksonXmlCData
        private String MusicUrl;

        @JacksonXmlCData
        private String HQMusicUrl;

        @JacksonXmlCData
        private String ThumbMediaId;
    }

}

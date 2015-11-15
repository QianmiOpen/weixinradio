package com.qianmi.weixin.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 回复语音消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class ResponseMessageVoice extends ResponseMessageBase {

    @Override
    public ResponseMsgType getMsgType() {
        return ResponseMsgType.voice;
    }

    private Voice voice;

    @Data class Voice {
        @JacksonXmlCData
        public String MediaId;
    }

}

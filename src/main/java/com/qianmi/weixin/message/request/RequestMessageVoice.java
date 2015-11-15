package com.qianmi.weixin.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 语音消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageVoice extends RequestMessageBase {

    @Override
    public RequestMsgType getMsgType() {
        return RequestMsgType.voice;
    }

    /**
     * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    @JacksonXmlCData
    private String MediaId;
    /**
     * 语音格式，如amr，speex等
     */
    @JacksonXmlCData
    private String Format;
    /**
     * 语音识别结果
     */
    @JacksonXmlCData
    private String Recognition;
}

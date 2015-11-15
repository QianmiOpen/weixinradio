package com.qianmi.weixin.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 视频消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageVideo extends RequestMessageBase {

    @Override
    public RequestMsgType getMsgType() {
        return RequestMsgType.video;
    }

    /**
     * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    @JacksonXmlCData
    private String MediaId;
    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    @JacksonXmlCData
    private String ThumbMediaId;
}

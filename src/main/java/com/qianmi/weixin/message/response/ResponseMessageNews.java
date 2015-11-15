package com.qianmi.weixin.message.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 回复图文消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class ResponseMessageNews extends ResponseMessageBase {

    @Override
    public ResponseMsgType getMsgType() {
        return ResponseMsgType.news;
    }

    private int ArticleCount;

    private List<item> Articles;

    @Data class item {
        @JacksonXmlCData
        private String Title;

        @JacksonXmlCData
        private String Description;

        @JacksonXmlCData
        private String PicUrl;

        @JacksonXmlCData
        private String Url;
    }

}

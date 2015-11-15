package com.qianmi.weixin.message.request.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 扫描带参数二维码
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageEvent_Scan extends RequestMessageEventBase {

    @Override
    public EventType getEvent() {
        return EventType.SCAN;
    }

    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     */
    @JacksonXmlCData
    private String EventKey;
    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    @JacksonXmlCData
    private String Ticket;
}

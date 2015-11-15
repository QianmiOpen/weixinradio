package com.qianmi.weixin.message.request.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 取消订阅事件
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageEvent_Unsubscribe extends RequestMessageEventBase {

    @Override
    public EventType getEvent() {
        return EventType.unsubscribe;
    }

    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     */
    @JacksonXmlCData
    private String EventKey;
}

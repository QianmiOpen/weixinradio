package com.qianmi.weixin.message.request.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 点击菜单跳转链接事件
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageEvent_View extends RequestMessageEventBase {

    @Override
    public EventType getEvent() {
        return EventType.VIEW;
    }

    /**
     * 事件KEY值，设置的跳转URL
     */
    @JacksonXmlCData
    private String EventKey;
}

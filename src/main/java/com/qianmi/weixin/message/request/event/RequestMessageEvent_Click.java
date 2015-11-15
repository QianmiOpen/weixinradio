package com.qianmi.weixin.message.request.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 自定义菜单
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageEvent_Click extends RequestMessageEventBase {

    @Override
    public EventType getEvent() {
        return EventType.CLICK;
    }

    /**
     * 事件KEY值，与自定义菜单接口中KEY值对应
     */
    @JacksonXmlCData
    private String EventKey;
}

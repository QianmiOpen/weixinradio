package com.qianmi.weixin.message.request.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 上报地理位置
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JacksonXmlRootElement(localName = "xml")
public class RequestMessageEvent_Location extends RequestMessageEventBase {

    @Override
    public EventType getEvent() {
        return EventType.LOCATION;
    }

    /**
     * 地理位置纬度
     */
    private Double Latitude;
    /**
     * 地理位置经度
     */
    private Double Longitude;
    /**
     * 地理位置精度
     */
    private Double Precision;
}

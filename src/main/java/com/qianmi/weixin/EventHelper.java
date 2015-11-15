package com.qianmi.weixin;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qianmi.weixin.message.request.event.EventType;

/**
 * 事件类型获取
 */
public class EventHelper {

    public static EventType getEventType(String msg) throws IOException {
        try {
            JsonNode json = new XmlMapper().readTree(msg);
            JsonNode event = json.get("Event");
            return EventType.valueOf(event.asText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EventType._UNKNOWN;
    }

}

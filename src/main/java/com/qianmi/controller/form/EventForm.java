package com.qianmi.controller.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qianmi.util.CustomDateTimeDeserializer;
import com.qianmi.util.CustomDateTimeSerializer;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * Event
 * Created by aqlu on 15/8/22.
 */
@Data
public class EventForm {

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime occurredTime;

    private String content;

    private String resourceId;

    private String workNo;

}

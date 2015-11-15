package com.qianmi.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qianmi.AppConstants;
import com.qianmi.util.CustomDateTimeDeserializer;
import com.qianmi.util.CustomDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 告警事件
 * Created by aqlu on 15/8/21.
 */
@Data
@AllArgsConstructor
@Document(indexName = AppConstants.ES_INDEX_NAME, type = "event")
public class Event {
    public Event(){}

    @Id
    private String id;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime occurredTime;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime confirmTime;

    private String workNo;

//    private EventLevel level;

//    private String title;

    private String content;

//    private String remark;

    private EventStatus status;

    private String resourceId;
}

package com.qianmi.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qianmi.AppConstants;
import com.qianmi.util.CustomDateTimeDeserializer;
import com.qianmi.util.CustomDateTimeSerializer;
import lombok.Data;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.http.HttpMethod;

/**
 * 资源信息
 * Created by aqlu on 15/8/21.
 */
@Data
@Document(indexName = AppConstants.ES_INDEX_NAME, type = "resource")
public class Resource implements Serializable{
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 工号
     */
    private String workNo;

    /**
     * 探测周期；单位：秒
     */
    private Integer detectPeriod;

    /**
     * 0:启用；1:禁用；2:可疑；3:故障；
     */
    private Integer status;

    /**
     * 最后一次探测时间
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime lastDetectTime;

    /**
     * 探测地址
     */
    private String url;

    /**
     * 探测参数
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * http headers
     */
    private Map<String, Object> headers = new HashMap<>();

    /**
     * 内容
     */
    private String body;

    /**
     * 请求方式
     */
    private HttpMethod method;

    /**
     * 维护周期，单位：秒，默认10分钟
     */
    private Integer maintainPeriod = 10*60;

    public Map<String, Object> getParams() {
        return Collections.unmodifiableMap(params);
    }

    public Map<String, Object> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public void putParams(String key, String value) {
        params.put(key, value);
    }

    public void putHeaders(String key, String value) {
        headers.put(key, value);
    }

    public void putAllParams(Map<String, Object> params){
        this.params.putAll(params);
    }

    public void putAllHeaders(Map<String, Object> headers){
        this.headers.putAll(headers);
    }
}

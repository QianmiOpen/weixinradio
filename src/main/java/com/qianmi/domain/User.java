package com.qianmi.domain;

import com.qianmi.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 用户
 * Created by aqlu on 15/8/21.
 */
@Data
@AllArgsConstructor
@Document(indexName = AppConstants.ES_INDEX_NAME, type = "user")
public class User {

    public User(){}

    public User(String workNo){
        this.workNo = workNo;
    }

    /**
     * 工号
     */
    @Id
    private String workNo;

    /**
     * 维护周期，单位：秒，默认10分钟
     */
    private Integer maintainPeriod = 10*60;

    /**
     * 用户最大资源数，默认：5
     */
    private Integer maxResource = 5;

    /**
     * 微信OpenId
     */
    private String wxOpenId;
}

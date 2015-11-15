package com.qianmi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * Created by aqlu on 15/8/22.
 */
@Data
@AllArgsConstructor
public class Pair implements Serializable{
    private Resource resource;

    private Integer retryCnt;
}

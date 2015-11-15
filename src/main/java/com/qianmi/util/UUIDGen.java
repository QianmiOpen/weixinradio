package com.qianmi.util;

import java.util.UUID;

/**
 * UUID
 * Created by aqlu on 15/8/21.
 */
public final class UUIDGen {
    public static String systemUuid(){
        return UUID.randomUUID().toString();
    }
}
package com.qianmi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局内存,常量
 */
public class AppConstants {

    public final static Map<String, String> caches = new ConcurrentHashMap<>();

    /**
     * 全局缓存assessToken
     */
    public final static String WEIXIN_ACCESS_TOKEN = "accessToken";

    /**
     * 微信二维码超时时间s
     */
    public final static String WEIXIN_QR_EXPIRE_SECONDS = "300";

    /**
     * json 响应头
     */
    public final static String MEDIA_TYPE_JSON = "application/json;charset=UTF-8";

    /**
     * ES索引名
     */
    public final static String ES_INDEX_NAME = "heimu";

    /**
     * 工号前缀
     */
    public final static String WORK_NO_PREFIX = "of";

}

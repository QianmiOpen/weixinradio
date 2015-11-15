package com.qianmi.weixin.template;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 告警通知
 * TM00541
 */
@Data
public class WarnTemplate implements TemplateBase {

    /**
     * 告警标题
     */
    private Map first =  new HashMap<String, String>() {{
        put("value", "云监控告警通知！");
        put("color", "#173177");
    }};

    /**
     * 告警内容
     */
    private Map content =  new HashMap<String, String>() {{
        put("value", "127.0.0.1剩余0G.");
        put("color", "#173177");
    }};

    /**
     * 告警发生时间
     */
    private Map occurtime =  new HashMap<String, String>() {{
        put("value", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        put("color", "#173177");
    }};

    /**
     * 备注
     */
    private Map remark =  new HashMap<String, String>() {{
        put("value", "请尽快处理");
        put("color", "#173177");
    }};

}

package com.qianmi.weixin.template;

import lombok.Data;

/**
 * 发送模板消息
 */
@Data
public class SendData {
    /**
     * OPENID
     */
    private String touser;
    /**
     * 模板ID
     */
    private String template_id;
    /**
     * 请求链接
     */
    private String url;
    /**
     * 主题颜色
     */
    private String topcolor = "#FF0000";

    private TemplateBase data;
}

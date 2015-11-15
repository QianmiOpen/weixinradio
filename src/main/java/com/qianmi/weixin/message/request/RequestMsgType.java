package com.qianmi.weixin.message.request;

/**
 * 发送消息类型
 */
public enum RequestMsgType {
    text,       // 文本消息
    image,      // 图片消息
    voice,      // 语音消息
    video,      // 视频消息
    shortvideo, // 小视频消息
    location,   // 地址位置消息
    link,       // 链接消息
    event,       // 事件消息


    _UNKNOWN // default
}

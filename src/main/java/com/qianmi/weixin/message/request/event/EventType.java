package com.qianmi.weixin.message.request.event;

/**
 * 事件类型枚举
 */
public enum EventType {
    subscribe,   // 订阅
    unsubscribe, // 取消订阅
    SCAN,        // 扫描带参数二维码
    LOCATION,    // 上报地理位置
    CLICK,       // 自定义菜单
    VIEW,        // 点击菜单跳转链接事件


    _UNKNOWN // default
}

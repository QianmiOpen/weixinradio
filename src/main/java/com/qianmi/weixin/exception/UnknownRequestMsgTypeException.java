package com.qianmi.weixin.exception;

/**
 * 无匹配请求消息类型
 */
public class UnknownRequestMsgTypeException extends Exception {
    public UnknownRequestMsgTypeException() {
    }

    public UnknownRequestMsgTypeException(String message) {
        super(message);
    }

    public UnknownRequestMsgTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}

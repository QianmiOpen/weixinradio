package com.qianmi.weixin.exception;

/**
 * 无匹配事件异常
 */
public class UnknownEventException extends Exception {
    public UnknownEventException() {
    }

    public UnknownEventException(String message) {
        super(message);
    }

    public UnknownEventException(String message, Throwable cause) {
        super(message, cause);
    }
}

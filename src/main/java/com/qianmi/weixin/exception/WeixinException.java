package com.qianmi.weixin.exception;

/**
 * 微信异常
 */
public class WeixinException extends Exception {
    public WeixinException(String message) {
        super(message);
    }

    public WeixinException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.lv.distributed.exception;

/**
 * @ProjectName: PluginException
 * @Author: lvminghui
 * @Description: 插件异常
 * @Date: 2022/9/15 15:05
 * @Version: 1.0
 */
public class PluginException extends RuntimeException{
    private static final long serialVersionUID = 8548771664564998595L;

    public PluginException() {
        super();
    }

    public PluginException(String message) {
        super(message);
    }

    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginException(Throwable cause) {
        super(cause);
    }
}

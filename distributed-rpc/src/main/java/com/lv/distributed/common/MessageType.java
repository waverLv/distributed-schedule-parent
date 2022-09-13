package com.lv.distributed.common;

public enum MessageType {
    REGISTER_REQ(1,"定时任务注册请求"),
    REGISTER_RESP(2,"定时任务注册响应"),
    HEART_REQ(3,"心跳请求"),
    HEART_RESP(4,"心跳响应"),
    INVOKE_REQ(5,"定时任务调用请求"),
    INVOKE_RESP(6,"定时任务调用响应"),
    LOGIN_REQ(7, "握手请求消息"),
    /** Login resp message type */
    LOGIN_RESP(8, "握手应答消息"),

    HEARTBEAT_REQ(9, "心跳请求"),

    HEARTBEAT_RESP(10, "心跳响应");

    private final byte code;
    private final String desc;
    
    MessageType(Integer code,String desc){
        this.code = code.byteValue();
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

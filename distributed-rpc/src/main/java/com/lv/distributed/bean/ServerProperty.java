package com.lv.distributed.bean;

/**
 * @ProjectName: ServerProperties
 * @Author: lvminghui
 * @Description: 服务端连接属性配置
 * @Date: 2022/9/29 10:49
 * @Version: 1.0
 */
public class ServerProperty {
    private String host;
    private int port;

    public ServerProperty(String host,int port){
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return  "{host=" + host + ", port=" + port +"}";
    }
}

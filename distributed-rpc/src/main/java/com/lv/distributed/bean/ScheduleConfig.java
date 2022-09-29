package com.lv.distributed.bean;

import java.util.List;

/**
 * @ProjectName: ScheduleConfig
 * @Author: lvminghui
 * @Description: 调度配置
 * @Date: 2022/9/27 10:32
 * @Version: 1.0
 */
public class ScheduleConfig {

    private String applicationName;
    private List<ServerProperty> serverProperties;
    private ServerProperty clientProperty;

    public String getApplicationName() {
        return applicationName;
    }

    public ScheduleConfig applicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public List<ServerProperty> getServerProperties() {
        return serverProperties;
    }

    public ScheduleConfig serverProperties(List<ServerProperty> serverProperties) {
        this.serverProperties = serverProperties;
        return this;
    }

    public ServerProperty getClientProperty() {
        return clientProperty;
    }

    public ScheduleConfig clientProperty(ServerProperty clientProperty) {
        this.clientProperty = clientProperty;
        return this;
    }
}

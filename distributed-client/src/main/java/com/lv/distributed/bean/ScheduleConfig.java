package com.lv.distributed.bean;

/**
 * @ProjectName: ScheduleConfig
 * @Author: lvminghui
 * @Description: 调度配置
 * @Date: 2022/9/27 10:32
 * @Version: 1.0
 */
public class ScheduleConfig {

    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public ScheduleConfig applicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }
}

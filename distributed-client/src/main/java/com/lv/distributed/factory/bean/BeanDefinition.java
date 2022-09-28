package com.lv.distributed.factory.bean;

import com.lv.distributed.bean.ScheduleConfig;

/**
 * @ProjectName: BeanDefination
 * @Author: lvminghui
 * @Description: bean defination
 * @Date: 2022/8/29 17:33
 * @Version: 1.0
 */
public class BeanDefinition {
    private Class type;
    private Object proxy;
    private String beanName;
    private Object target;
    private ScheduleConfig scheduleConfig;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public ScheduleConfig getScheduleConfig() {
        return scheduleConfig;
    }

    public void setScheduleConfig(ScheduleConfig scheduleConfig) {
        this.scheduleConfig = scheduleConfig;
    }
}

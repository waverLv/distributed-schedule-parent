package com.lv.distributed.factory.bean;

import java.util.Map;

public interface ScheduleBeanFactory {

    public Object getBean(String name);

    public Map<String,BeanDefinition> getBean(Class classType);

    public void registerBeans();

    public void start();
}

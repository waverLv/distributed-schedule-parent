package com.lv.distributed.factory.method;

import com.lv.distributed.annotation.DistributeSchedule;
import com.lv.distributed.factory.bean.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodDefinition {

    private Method method;
    private String name;
    private BeanDefinition beanDefinition;
    private String loadBalance;
    private String faultTolerant;
    private String description;
    private Boolean start;
    private String cron;


    private MethodDefinition(){

    }

    public static MethodDefinition instance(){
        return new MethodDefinition();
    }

    public Method getMethod() {
        return method;
    }

    public MethodDefinition method(Method method) {
        this.method = method;
        return this;
    }

    public String getName() {
        return name;
    }

    public MethodDefinition name(String name) {
        this.name = name;
        return this;
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public MethodDefinition beanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinition = beanDefinition;
        return this;
    }

    public MethodDefinition distributeSchedule(DistributeSchedule distributeSchedule){
        this.cron = distributeSchedule.cron();
        this.description = distributeSchedule.desc();
        this.faultTolerant = distributeSchedule.faultTolerant();
        this.loadBalance = distributeSchedule.loadBalance();
        this.start = distributeSchedule.start();
        return this;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public String getFaultTolerant() {
        return faultTolerant;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getStart() {
        return start;
    }

    public String getCron() {
        return cron;
    }

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        int parameterCount = method.getParameterCount();
        if(parameterCount > 0){
            return  method.invoke(beanDefinition.getProxy(),args);
        }
        return method.invoke(beanDefinition.getProxy());
    }
}

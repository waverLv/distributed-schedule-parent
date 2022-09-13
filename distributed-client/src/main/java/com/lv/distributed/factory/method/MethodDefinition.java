package com.lv.distributed.factory.method;

import com.lv.distributed.factory.bean.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodDefinition {

    private Method method;
    private String name;
    private BeanDefinition beanDefinition;

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

    public Object invoke() throws InvocationTargetException, IllegalAccessException {
       return  method.invoke(beanDefinition.getProxy(),null);
    }

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
       return  method.invoke(beanDefinition.getProxy(),args);
    }
}

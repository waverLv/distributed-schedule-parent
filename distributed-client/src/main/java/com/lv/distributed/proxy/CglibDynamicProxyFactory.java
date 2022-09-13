package com.lv.distributed.proxy;



import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ProjectName: CglibDynamicProxyFactory
 * @Author: lvminghui
 * @Description: CGLIB动态代理、
 * @Date: 2022/8/26 11:51
 * @Version: 1.0
 */
public class CglibDynamicProxyFactory<T> implements DynamicProxyFactory<T>, MethodInterceptor {

    private Object targetObject;

    @Override
    public <T> T newProxy(T targetObject) {
        this.targetObject = targetObject;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetObject.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return method.invoke(targetObject,args);
    }

}

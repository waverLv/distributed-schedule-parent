package com.lv.distributed.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ProjectName: JdkDynamicProxyFactory
 * @Author: lvminghui
 * @Description: JDK动态代理
 * @Date: 2022/8/26 11:50
 * @Version: 1.0
 */
public class JdkDynamicProxyFactory<T> implements DynamicProxyFactory<T>{
    private Object targetObject;
    @Override
    public <T> T newProxy(T targetObject) {
        this.targetObject = targetObject;
        Class<?> targetClass = targetObject.getClass();
        return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(targetObject,args);
            }
        });
    }

   /* public static void main(String[] args) {
        JdkDynamicProxyFactory<> factory = new JdkDynamicProxyFactory();
        Service service = factory.newProxy(new ServiceImpl("bob"));
        service.morning();
        CglibDynamicProxyFactory cgFactory = new CglibDynamicProxyFactory();
        ServiceImpl tom = cgFactory.newProxy(new ServiceImpl("Tom"));
        BeanUtil.setFieldValue(tom,"name","john");
        tom.morning();

    }*/

    public static class ServiceImpl implements Service{
        private String name;

        public ServiceImpl(){

        }

        public ServiceImpl(String name){
            this.name = name;
        }

        @Override
        public void morning() {
            System.out.println("Hello "+name);
        }
    }

    public interface Service{
        public void morning();
    }



}

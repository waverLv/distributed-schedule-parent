package com.lv.distributed.proxy;

/**
 * 代理工厂
 */
public interface DynamicProxyFactory<T> {

    public <T> T newProxy(T targetObject);
}

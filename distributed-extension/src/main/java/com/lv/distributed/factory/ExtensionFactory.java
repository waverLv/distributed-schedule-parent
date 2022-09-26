package com.lv.distributed.factory;

import com.lv.distributed.annotation.SPI;

import java.util.Map;

/**
 * @ProjectName: ExtensionFactory
 * @Author: lvminghui
 * @Description: 扩展工厂
 * @Date: 2022/9/15 14:33
 * @Version: 1.0
 */
@SPI
public interface ExtensionFactory {

    /**
     * 根据key获取指定扩展类
     *
     * @param <T>   类型参数
     * @param key   扩展类key
     * @param clazz 类型参数 clazz
     * @return the extenstion
     */
    <T> T getExtension(String key, Class<T> clazz);

    /**
     * 获取注解配置扩展类
     * @param clazz 类型参数 clazz
     * @param <T>  类型参数
     * @return
     */
    <T> T getExtension(Class<T> clazz);

    /**
     * 获取所有扩展类
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Map<String,T> getAllExtensionList(Class<T> clazz);
}

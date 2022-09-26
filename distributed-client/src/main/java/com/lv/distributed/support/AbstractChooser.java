package com.lv.distributed.support;

import com.lv.distributed.api.SupportStrategy;
import com.lv.distributed.factory.ExtensionFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: AbstractChooser
 * @Author: lvminghui
 * @Description: chooser 抽象类
 * @Date: 2022/9/16 17:03
 * @Version: 1.0
 */
public abstract class AbstractChooser<T extends SupportStrategy> implements Chooser<T>{

    private ExtensionFactory extensionFactory;
    private Class<T> clazz;
    private Map<String,T> strategyMap = new ConcurrentHashMap<>();

    public AbstractChooser(ExtensionFactory extensionFactory,Class<T> clazz){
        this.extensionFactory = extensionFactory;
        this.clazz = clazz;
        load();
    }

    @Override
    public <T> T choose(String strategyName) {
        return (T) strategyMap.get(strategyName);
    }

    @Override
    public void load() {
        strategyMap.putAll(extensionFactory.getAllExtensionList(clazz));
    }

    public AbstractChooser add(String strategyName,T strategy){
        strategyMap.put(strategyName,strategy);
        return this;
    }
}

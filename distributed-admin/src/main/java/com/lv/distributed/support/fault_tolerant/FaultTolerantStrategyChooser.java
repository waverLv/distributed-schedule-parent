package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.support.Chooser;
import com.lv.distributed.api.InvokeStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public  class FaultTolerantStrategyChooser implements Chooser {

    private Map<String,InvokeStrategy> strategyMap = new ConcurrentHashMap<>();

    @Override
    public InvokeStrategy choose(String strategyName){
        return strategyMap.get(strategyName);
    }

    @Override
    public Map<String,InvokeStrategy> load(){
        strategyMap.putIfAbsent("failover",new FailoverFaultTolerantStrategy());
        return strategyMap;
    }
}

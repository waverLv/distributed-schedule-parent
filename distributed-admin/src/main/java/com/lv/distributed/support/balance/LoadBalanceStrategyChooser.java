package com.lv.distributed.support.balance;

import com.lv.distributed.support.Chooser;
import com.lv.distributed.api.InvokeStrategy;

import java.util.Map;

public  class LoadBalanceStrategyChooser implements Chooser {

    @Override
    public InvokeStrategy choose(String strategeyName) {
        return null;
    }

    @Override
    public Map<String,InvokeStrategy> load() {
        return null;
    }
}

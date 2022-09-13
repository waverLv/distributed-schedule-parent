package com.lv.distributed.support;

import com.lv.distributed.api.InvokeStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public class InvokeStrategyAdapter implements InvokeStrategy {
    @Override
    public void invoke(DistributeTaskBO distributeTaskBO) {

    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskResponseWrapper response) {
        return null;
    }
}

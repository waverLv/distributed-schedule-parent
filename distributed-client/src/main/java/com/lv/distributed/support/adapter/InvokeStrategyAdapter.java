package com.lv.distributed.support.adapter;

import com.lv.distributed.api.InvokeStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.pipeline.InvokeStrategyPipeline;

public abstract class InvokeStrategyAdapter implements InvokeStrategy {

    protected InvokeStrategyPipeline invokeStrategyPipeline;

    public InvokeStrategyAdapter(InvokeStrategyPipeline invokeStrategyPipeline){
        this.invokeStrategyPipeline = invokeStrategyPipeline;
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        return invokeStrategyPipeline.fireInvoke(distributeTaskBO);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskRequestWrapper requestWrapper) {
        return null;
    }

    public InvokeStrategyPipeline pipeline(){
        return this.invokeStrategyPipeline;
    }
}

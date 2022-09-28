package com.lv.distributed.support.adapter;

import com.lv.distributed.support.pipeline.InvokeStrategyPipeline;
import com.lv.distributed.support.pipeline.InvokeStrategyPipelineFactory;

public class DefaultInvokeStrategyAdapter extends InvokeStrategyAdapter {


    public DefaultInvokeStrategyAdapter(InvokeStrategyPipeline invokeStrategyPipeline){
        super(invokeStrategyPipeline);
    }
    public DefaultInvokeStrategyAdapter(InvokeStrategyPipelineFactory pipelineFactory){
        super(pipelineFactory.newInstance());
    }


}

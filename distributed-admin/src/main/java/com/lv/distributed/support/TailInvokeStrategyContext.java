package com.lv.distributed.support;

import com.lv.distributed.bean.*;
import com.lv.distributed.factory.executor.DefaultExecutorExecutorContext;
import com.lv.distributed.factory.executor.ExecutorContext;

/**
 * 尾部策略上下文
 */
public  class TailInvokeStrategyContext extends InvokeStrategyContext{

    private ExecutorContext executorContext;

    public TailInvokeStrategyContext() {
        super(null,null);
        if(executorContext == null){
            executorContext = new DefaultExecutorExecutorContext();
        }
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        DistributeTaskBOWrapper wrapper = (DistributeTaskBOWrapper) distributeTaskBO;
        executorContext.submit(wrapper.getDistributeTask());
        return null;
    }
}

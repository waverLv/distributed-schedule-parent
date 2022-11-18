package com.lv.distributed.support.context;

import com.lv.distributed.bean.*;
import com.lv.distributed.executor.DefaultExecutorExecutorContext;
import com.lv.distributed.executor.ExecutorContext;
import org.springframework.beans.BeanUtils;

import java.util.concurrent.CompletableFuture;

/**
 * 尾部策略容器
 */
public  class TailInvokeStrategyContext extends InvokeStrategyContext{

    private ExecutorContext executorContext;
    private DistributeTaskFactory distributeTaskFactory = new DefaultDistributeTaskFactory();;


    public TailInvokeStrategyContext() {
        super(null);
        if(executorContext == null){
            executorContext = new DefaultExecutorExecutorContext();
        }
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper) {
        CompletableFuture completableFuture = asyncInvoke(wrapper);
        try{
            return (DistributeTaskResponseWrapper) completableFuture.get();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public CompletableFuture asyncInvoke(DistributeTaskBOWrapper wrapper) {

        //TODO 超时时间优化
        DistributeCompletableFuture future = DistributeCompletableFuture.newFuture(distributeTask, wrapper.getTimeout());
        return future;
    }


}

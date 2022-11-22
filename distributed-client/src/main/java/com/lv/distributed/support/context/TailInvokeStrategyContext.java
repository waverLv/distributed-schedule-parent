package com.lv.distributed.support.context;

import com.lv.distributed.bean.*;
import com.lv.distributed.executor.DefaultExecutorContext;
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
            executorContext = new DefaultExecutorContext();
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
        DistributeTask task = newTask(wrapper);
        DistributeCompletableFuture future = DistributeCompletableFuture.newFuture(task,wrapper.getTimeout(),executorContext);
        return future;
    }

    private DistributeTask newTask(DistributeTaskBOWrapper wrapper){
        DistributeRequestBody body = requestBody(wrapper);
        return distributeTaskFactory.newTask(body, wrapper.isStart(), wrapper.getCtx());

    }

    private DistributeRequestBody requestBody(DistributeTaskBOWrapper wrapper){
        DistributeRequestBody body = new DistributeRequestBody();
        BeanUtils.copyProperties(wrapper,body);
        return body;
    }


}

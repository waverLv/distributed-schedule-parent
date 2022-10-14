package com.lv.distributed.support.context;

import com.lv.distributed.bean.*;
import com.lv.distributed.executor.DefaultExecutorExecutorContext;
import com.lv.distributed.executor.ExecutorContext;
import org.springframework.beans.BeanUtils;

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
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        DistributeTaskBOWrapper wrapper = (DistributeTaskBOWrapper) distributeTaskBO;
        DistributeTask distributeTask = newTask(wrapper);
        executorContext.submit(distributeTask);
        return null;
    }


    private DistributeTask newTask(DistributeTaskBOWrapper wrapper){
        DistributeRequestBody body = requestBody(wrapper);
        return distributeTaskFactory.newTask(body, wrapper.isStart(), wrapper.getCtx());

    }

    private DistributeRequestBody requestBody(DistributeTaskBO distributeTaskBO){
        DistributeRequestBody body = new DistributeRequestBody();
        BeanUtils.copyProperties(distributeTaskBO,body);
        return body;
    }
}

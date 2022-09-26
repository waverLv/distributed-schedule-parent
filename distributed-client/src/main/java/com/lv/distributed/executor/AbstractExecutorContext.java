package com.lv.distributed.executor;

import com.lv.distributed.bean.DistributeTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractExecutorContext implements ExecutorContext {
    private ExecutorService executorService;

    public AbstractExecutorContext(ExecutorService executorService){
        if(null == executorService){
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),new DefaultThreadFactory());
        }
        this.executorService = executorService;
    }

    @Override
    public void submit(DistributeTask task){
        executorService.submit(task);
    }

}

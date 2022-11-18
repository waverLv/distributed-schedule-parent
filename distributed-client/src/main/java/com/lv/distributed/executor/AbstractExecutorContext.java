package com.lv.distributed.executor;

import com.lv.distributed.bean.DistributeTask;

import java.util.concurrent.*;

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
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new DefaultThreadFactory());
    }

}

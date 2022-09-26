package com.lv.distributed.executor;

import java.util.concurrent.ExecutorService;

public class DefaultExecutorExecutorContext extends AbstractExecutorContext {


    public DefaultExecutorExecutorContext(){
        this(null);
    }

    public DefaultExecutorExecutorContext(ExecutorService executorService){
       super(executorService);
    }
}

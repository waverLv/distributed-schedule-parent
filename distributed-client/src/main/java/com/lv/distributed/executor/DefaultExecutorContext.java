package com.lv.distributed.executor;

import java.util.concurrent.ExecutorService;

public class DefaultExecutorContext extends AbstractExecutorContext {


    public DefaultExecutorContext(){
        this(null);
    }

    public DefaultExecutorContext(ExecutorService executorService){
       super(executorService);
    }
}

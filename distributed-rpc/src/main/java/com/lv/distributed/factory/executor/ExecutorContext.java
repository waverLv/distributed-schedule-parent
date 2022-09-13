package com.lv.distributed.factory.executor;

import com.lv.distributed.bean.DistributeTask;

public interface ExecutorContext {

    public void submit(DistributeTask task);
}

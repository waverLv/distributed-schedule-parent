package com.lv.distributed.executor;

import com.lv.distributed.bean.DistributeTask;

public interface ExecutorContext {

    public void submit(DistributeTask task);
}

package com.lv.distributed.support.pipeline;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponse;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;
import com.lv.distributed.support.InvokeStrategyContext;

public interface InvokeStrategyPipeline {

    public void addLast(InvokeStrategyContext context);
    public void addLast(Chooser chooser);

    public void addFirst(InvokeStrategyContext context);
    public void addFirst(Chooser chooser);

    public DistributeTaskResponseWrapper fireInvoke(DistributeTaskBO distributeTaskBO);

}

package com.lv.distributed.api;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public interface InvokeStrategy {

    public void invoke(DistributeTaskBO distributeTaskBO);

    public DistributeTaskResponseWrapper invoke(DistributeTaskResponseWrapper response);
}

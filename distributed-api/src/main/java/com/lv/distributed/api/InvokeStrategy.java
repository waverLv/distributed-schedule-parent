package com.lv.distributed.api;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public interface InvokeStrategy {

    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO);

    public DistributeTaskResponseWrapper invoke(DistributeTaskRequestWrapper response);

}

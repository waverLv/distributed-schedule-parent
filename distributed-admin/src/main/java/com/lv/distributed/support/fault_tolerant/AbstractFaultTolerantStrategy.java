package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.api.InvokeStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public abstract class  AbstractFaultTolerantStrategy implements FaultTolerantStrategy, InvokeStrategy {

    @Override
    public void invoke(DistributeTaskBO distributeTaskBO) {
        fault(distributeTaskBO);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskResponseWrapper response) {
        return fault(response);
    }
}

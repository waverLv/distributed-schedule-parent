package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.api.FaultTolerantStrategy;
import com.lv.distributed.api.InvokeStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public abstract class  AbstractFaultTolerantStrategy implements FaultTolerantStrategy, InvokeStrategy {

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        fault(distributeTaskBO);
        return null;
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskRequestWrapper requestWrapper) {
        return fault(requestWrapper);
    }
}

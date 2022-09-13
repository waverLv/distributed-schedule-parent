package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public class FailoverFaultTolerantStrategy extends AbstractFaultTolerantStrategy{


    @Override
    public void fault(DistributeTaskBO distributeTaskBO) {

    }

    @Override
    public DistributeTaskResponseWrapper fault(DistributeTaskResponseWrapper response) {
        return null;
    }
}

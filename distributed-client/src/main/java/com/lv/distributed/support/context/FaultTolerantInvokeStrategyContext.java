package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;

public class FaultTolerantInvokeStrategyContext extends InvokeStrategyContext {

    public FaultTolerantInvokeStrategyContext(Chooser chooser) {
        super(chooser,null);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        DistributeTaskResponseWrapper response = this.next.invoke(distributeTaskBO);
//        return invokeStrategy().invoke(distributeTaskBO);
        return null;
    }
}

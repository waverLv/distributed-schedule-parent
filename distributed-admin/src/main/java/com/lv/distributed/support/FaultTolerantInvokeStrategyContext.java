package com.lv.distributed.support;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public class FaultTolerantInvokeStrategyContext extends InvokeStrategyContext {

    public FaultTolerantInvokeStrategyContext(Chooser chooser) {
        super(chooser,null);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        DistributeTaskResponseWrapper response = this.next.invoke(distributeTaskBO);
        return getInvokeStrategy().invoke(response);
    }
}

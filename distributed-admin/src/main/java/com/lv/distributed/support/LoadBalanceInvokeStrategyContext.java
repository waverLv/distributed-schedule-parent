package com.lv.distributed.support;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public class LoadBalanceInvokeStrategyContext extends InvokeStrategyContext {

    public LoadBalanceInvokeStrategyContext(Chooser chooser) {
        super(chooser,null);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        return this.next.invoke(distributeTaskBO);
    }
}

package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;

public class LoadBalanceInvokeStrategyContext extends InvokeStrategyContext {

    public LoadBalanceInvokeStrategyContext(Chooser chooser) {
        super(chooser,null);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        return this.next.invoke(distributeTaskBO);
    }
}

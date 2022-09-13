package com.lv.distributed.support;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public class DefaultInvokeStrategyContext extends InvokeStrategyContext {

    public DefaultInvokeStrategyContext(Chooser chooser) {
        super(chooser,null);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        this.next.invoke(distributeTaskBO);
        return null;
    }
}

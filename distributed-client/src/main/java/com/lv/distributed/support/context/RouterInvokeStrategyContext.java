package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;

public class RouterInvokeStrategyContext extends InvokeStrategyContext {

    public RouterInvokeStrategyContext(Chooser chooser, String strategeyName) {
        super(chooser, strategeyName);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        return null;
    }
}

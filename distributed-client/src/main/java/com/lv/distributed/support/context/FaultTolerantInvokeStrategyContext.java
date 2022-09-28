package com.lv.distributed.support.context;

import com.lv.distributed.api.FaultTolerantStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;

public class FaultTolerantInvokeStrategyContext<T extends FaultTolerantStrategy> extends InvokeStrategyContext<T> {

    public FaultTolerantInvokeStrategyContext(Chooser chooser) {
        super(chooser);
    }

    @Override
    public void wrapper(DistributeTaskBO distributeTaskBO) {

    }


}

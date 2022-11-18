package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.chooser.Chooser;

public class DefaultInvokeStrategyContext extends InvokeStrategyContext {

    public DefaultInvokeStrategyContext(Chooser chooser) {
        super(chooser);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper) {
        next().invoke(wrapper);
        return null;
    }
}

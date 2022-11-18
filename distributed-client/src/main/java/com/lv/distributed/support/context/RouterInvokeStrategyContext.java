package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.factory.register.RegisterChannelContext;
import com.lv.distributed.support.chooser.Chooser;

public class RouterInvokeStrategyContext extends InvokeStrategyContext {

    public RouterInvokeStrategyContext(Chooser chooser) {
        super(chooser);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper) {
        wrapper.setCtx(RegisterChannelContext.get(wrapper.getApplicationName()).get(0));
        return next().invoke(wrapper);
    }
}

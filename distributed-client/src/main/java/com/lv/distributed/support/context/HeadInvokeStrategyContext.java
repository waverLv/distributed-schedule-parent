package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

/**
 * 头部策略容器
 */
public  class HeadInvokeStrategyContext extends InvokeStrategyContext{

    public HeadInvokeStrategyContext() {
        super(null,null);
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        return this.next.invoke(distributeTaskBO);
    }
}

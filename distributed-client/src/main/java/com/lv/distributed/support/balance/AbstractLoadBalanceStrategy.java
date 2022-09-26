package com.lv.distributed.support.balance;

import com.lv.distributed.api.InvokeStrategy;
import com.lv.distributed.api.LoadBalanceStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public abstract class AbstractLoadBalanceStrategy implements LoadBalanceStrategy, InvokeStrategy {

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO) {
        balance(distributeTaskBO);
        return null;
    }
}

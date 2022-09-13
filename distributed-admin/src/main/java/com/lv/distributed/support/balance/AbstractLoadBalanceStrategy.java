package com.lv.distributed.support.balance;

import com.lv.distributed.api.InvokeStrategy;
import com.lv.distributed.bean.DistributeTaskBO;

public abstract class AbstractLoadBalanceStrategy implements LoadBalanceStrategy, InvokeStrategy {

    @Override
    public void invoke(DistributeTaskBO distributeTaskBO) {
        balance(distributeTaskBO);
    }
}

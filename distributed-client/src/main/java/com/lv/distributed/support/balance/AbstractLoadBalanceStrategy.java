package com.lv.distributed.support.balance;

import com.lv.distributed.api.LoadBalanceStrategy;
import com.lv.distributed.bean.DistributeTaskBO;

public abstract class AbstractLoadBalanceStrategy implements LoadBalanceStrategy{
    @Override
    public void balance(DistributeTaskBO distributeTaskBO) {
        choose(distributeTaskBO);
    }
}

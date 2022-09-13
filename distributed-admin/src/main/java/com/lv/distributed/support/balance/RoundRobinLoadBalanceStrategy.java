package com.lv.distributed.support.balance;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public class RoundRobinLoadBalanceStrategy extends AbstractLoadBalanceStrategy{


    @Override
    public void balance(DistributeTaskBO distributeTaskBO) {

    }


    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskResponseWrapper response) {
        return null;
    }
}

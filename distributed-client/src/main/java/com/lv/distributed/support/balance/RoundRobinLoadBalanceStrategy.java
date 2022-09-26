package com.lv.distributed.support.balance;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public class RoundRobinLoadBalanceStrategy extends AbstractLoadBalanceStrategy{


    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskRequestWrapper response) {
        return null;
    }

    @Override
    public void balance(DistributeTaskBO distributeTaskBO) {

    }
}

package com.lv.distributed.support.context;

import com.lv.distributed.api.LoadBalanceStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;

public class LoadBalanceInvokeStrategyContext<T extends LoadBalanceStrategy> extends InvokeStrategyContext<T> {

    public LoadBalanceInvokeStrategyContext(Chooser chooser) {
        super(chooser);
    }

    @Override
    public void wrapper(DistributeTaskBO distributeTaskBO) {
        LoadBalanceStrategy strategy = chooser.choose(distributeTaskBO.getBalanceName());
        strategy.balance(distributeTaskBO);
    }


}

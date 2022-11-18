package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.chooser.Chooser;

public class LoadBalanceInvokeStrategyContext<T extends LoadBalanceStrategy> extends InvokeStrategyContext<T> {

    public LoadBalanceInvokeStrategyContext(Chooser chooser) {
        super(chooser);
    }

    @Override
    public void wrapper(DistributeTaskBOWrapper wrapper) {
        LoadBalanceStrategy strategy = chooser.choose(wrapper.getBalanceName());
        strategy.balance(wrapper,null);
    }


}

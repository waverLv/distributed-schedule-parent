package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.chooser.Chooser;
import com.lv.distributed.support.tolerant.FaultTolerantStrategy;

public class FaultTolerantInvokeStrategyContext<T extends FaultTolerantStrategy,L extends LoadBalanceStrategy> extends InvokeStrategyContext<T> {

    private Chooser<L> loadBalanceChooser;

    public FaultTolerantInvokeStrategyContext(Chooser<T> tolerantChooser, Chooser<L> loadBalanceChooser) {
        super(tolerantChooser);
        this.loadBalanceChooser = loadBalanceChooser;
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper) {
        T tolerantStrategy = chooser.choose(wrapper.getFaultTolerantName());
        L balanceStrategy = loadBalanceChooser.choose(wrapper.getBalanceName());
        return tolerantStrategy.invoke(wrapper,next,balanceStrategy);
    }

}

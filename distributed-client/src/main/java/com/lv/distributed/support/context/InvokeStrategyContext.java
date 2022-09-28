package com.lv.distributed.support.context;

import com.lv.distributed.api.LoadBalanceStrategy;
import com.lv.distributed.api.SupportStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;

public abstract class InvokeStrategyContext<T> {

    protected Chooser<T> chooser;

    public InvokeStrategyContext prev;
    public InvokeStrategyContext next;

    public InvokeStrategyContext(Chooser chooser){
        this.chooser = chooser;
    }

    public  DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO){
        wrapper(distributeTaskBO);
        return this.next.invoke(distributeTaskBO);
    }

    public  void wrapper(DistributeTaskBO distributeTaskBO){

    }


}

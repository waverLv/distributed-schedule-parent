package com.lv.distributed.support;

import com.lv.distributed.api.InvokeStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

public abstract class InvokeStrategyContext {

    private InvokeStrategy invokeStrategy;
    private Chooser chooser;

    public InvokeStrategyContext prev;
    public InvokeStrategyContext next;

    public InvokeStrategyContext(Chooser chooser,String strategeyName){
        this.chooser = chooser;
        this.invokeStrategy = chooser.choose(strategeyName);
    }

    public abstract DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO);

    public InvokeStrategy getInvokeStrategy(){
        return this.invokeStrategy;
    }

}

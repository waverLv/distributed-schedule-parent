package com.lv.distributed.support.context;

import com.lv.distributed.api.SupportStrategy;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.Chooser;

public abstract class InvokeStrategyContext<T extends SupportStrategy> {

    private T invokeStrategy;
    private Chooser<T> chooser;

    public InvokeStrategyContext prev;
    public InvokeStrategyContext next;

    public InvokeStrategyContext(Chooser chooser,String strategeyName){
        this.chooser = chooser;
        this.invokeStrategy = (T) chooser.choose(strategeyName);
    }

    public abstract DistributeTaskResponseWrapper invoke(DistributeTaskBO distributeTaskBO);

    public T invokeStrategy(){
        return this.invokeStrategy;
    }

}

package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.chooser.Chooser;

import java.util.concurrent.CompletableFuture;

public abstract class InvokeStrategyContext<T> implements StrategyContext {

    protected Chooser<T> chooser;

    public InvokeStrategyContext prev;
    public InvokeStrategyContext next;

    public InvokeStrategyContext(Chooser<T> chooser){
        this.chooser = chooser;
    }

    @Override
    public  DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper){
        wrapper(wrapper);
        return this.next.invoke(wrapper);
    }

    @Override
    public  CompletableFuture asyncInvoke(DistributeTaskBOWrapper wrapper) {
         wrapper(wrapper);
        return this.next.asyncInvoke(wrapper);
    }

    public  void wrapper(DistributeTaskBOWrapper wrapper){

    }

    @Override
    public StrategyContext next() {
        return this.next;
    }

    @Override
    public StrategyContext head() {
        return this.prev;
    }
}

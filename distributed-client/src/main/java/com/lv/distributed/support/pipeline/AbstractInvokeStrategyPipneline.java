package com.lv.distributed.support.pipeline;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.*;
import com.lv.distributed.support.context.DefaultInvokeStrategyContext;
import com.lv.distributed.support.context.HeadInvokeStrategyContext;
import com.lv.distributed.support.context.InvokeStrategyContext;
import com.lv.distributed.support.context.TailInvokeStrategyContext;

public abstract class AbstractInvokeStrategyPipneline implements InvokeStrategyPipeline {
    protected final InvokeStrategyContext headInvokeStrategyContext;
    protected final InvokeStrategyContext tailInvokeStrategyContext;

    public AbstractInvokeStrategyPipneline(){
        this.headInvokeStrategyContext = new HeadInvokeStrategyContext();
        this.tailInvokeStrategyContext = new TailInvokeStrategyContext();
        this.headInvokeStrategyContext.next = tailInvokeStrategyContext;
        this.tailInvokeStrategyContext.prev = headInvokeStrategyContext;
    }


    @Override
    public void addLast(InvokeStrategyContext context) {
        InvokeStrategyContext prev = this.tailInvokeStrategyContext.prev;
        prev.next = context;
        context.prev = prev;
        context.next = tailInvokeStrategyContext;
        this.tailInvokeStrategyContext.prev = context;

    }

    @Override
    public void addFirst(InvokeStrategyContext context) {
        InvokeStrategyContext next = this.headInvokeStrategyContext.next;
        next.prev = context;
        context.next = next;
        context.prev = headInvokeStrategyContext;
        this.headInvokeStrategyContext.next = context;
    }

    @Override
    public void addLast(Chooser chooser) {
        DefaultInvokeStrategyContext context = new DefaultInvokeStrategyContext(chooser);
        addLast(context);
    }

    @Override
    public void addFirst(Chooser chooser) {
        DefaultInvokeStrategyContext context = new DefaultInvokeStrategyContext(chooser);
        addFirst(context);
    }

    @Override
    public DistributeTaskResponseWrapper fireInvoke(DistributeTaskBO distributeTaskBO) {
        return  this.headInvokeStrategyContext.invoke(distributeTaskBO);
    }




}

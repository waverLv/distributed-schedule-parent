package com.lv.distributed.support.tolerant;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.InvokeStrategy;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.context.StrategyContext;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class  AbstractFaultTolerantStrategy implements FaultTolerantStrategy, InvokeStrategy {


    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskRequestWrapper requestWrapper) {
        return null;
    }


    public  void select(DistributeTaskBOWrapper wrapper, LoadBalanceStrategy loadBalanceStrategy, List<ChannelHandlerContext> invokedList){
        loadBalanceStrategy.balance(wrapper,invokedList);
    }

    public  DistributeTaskResponseWrapper doInvoke(DistributeTaskBOWrapper wrapper, StrategyContext context){
        return context.invoke(wrapper);
    }
}

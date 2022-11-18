package com.lv.distributed.support.balance;

import com.lv.distributed.bean.DistributeTaskBOWrapper;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public abstract class AbstractLoadBalanceStrategy implements LoadBalanceStrategy{
    @Override
    public void balance(DistributeTaskBOWrapper wrapper, List<ChannelHandlerContext> invokedList) {
        choose(wrapper);
    }
}

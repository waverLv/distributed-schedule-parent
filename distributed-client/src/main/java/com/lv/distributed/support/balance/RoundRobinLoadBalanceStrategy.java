package com.lv.distributed.support.balance;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.factory.register.RegisterChannelContext;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class RoundRobinLoadBalanceStrategy extends AbstractLoadBalanceStrategy{

    @Override
    public void balance(DistributeTaskBO distributeTaskBO) {
        DistributeTaskBOWrapper wrapper = (DistributeTaskBOWrapper) distributeTaskBO;
        List<ChannelHandlerContext> channelHandlerContexts = RegisterChannelContext.get(wrapper.getApplicationName());
        wrapper.setCtx(channelHandlerContexts.get(0));
    }
}

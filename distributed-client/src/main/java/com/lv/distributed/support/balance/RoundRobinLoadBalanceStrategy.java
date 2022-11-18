package com.lv.distributed.support.balance;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.factory.register.RegisterChannelContext;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ProjectName: RoundRobinLoadBalanceStrategy
 * @Author: lvminghui
 * @Description: 轮询负载策略
 * @Date: 2022/10/12 11:19
 * @Version: 1.0
 */
public class RoundRobinLoadBalanceStrategy extends AbstractLoadBalanceStrategy{
    private static  final Logger LOGGER  = LoggerFactory.getLogger(RoundRobinLoadBalanceStrategy.class);

    Map<String, AtomicInteger> roundRobinCounter = new ConcurrentHashMap<>();

    @Override
    public void choose(DistributeTaskBOWrapper wrapper) {
        AtomicInteger counter = roundRobinCounter.get(wrapper.getApplicationName());
        if(null == counter){
            counter = new AtomicInteger(0);
            roundRobinCounter.put(wrapper.getApplicationName(),counter);
        }
        List<ChannelHandlerContext> chcs = RegisterChannelContext.get(wrapper.getApplicationName());
        int index = counter.incrementAndGet() % chcs.size();
        wrapper.setCtx(chcs.get(index));
    }

}

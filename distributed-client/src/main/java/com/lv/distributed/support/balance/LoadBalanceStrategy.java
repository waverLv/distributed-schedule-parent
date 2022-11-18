package com.lv.distributed.support.balance;

import com.lv.distributed.annotation.SPI;
import com.lv.distributed.api.SupportStrategy;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * 负载均衡接口
 */
@SPI
public interface LoadBalanceStrategy extends SupportStrategy {

    public void balance(DistributeTaskBOWrapper wrapper, List<ChannelHandlerContext> invokedList);

    public void choose(DistributeTaskBOWrapper wrapper);


}

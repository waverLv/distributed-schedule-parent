package com.lv.distributed.support.tolerant;

import com.lv.distributed.annotation.SPI;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.SupportStrategy;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.context.StrategyContext;

/**
 * 容错接口
 */
@SPI
public interface FaultTolerantStrategy extends SupportStrategy {

    public DistributeTaskResponseWrapper invoke(DistributeTaskRequestWrapper requestWrapper);
    /**
     * 1、调用负载策略，指定调用服务器
     * 2、调用下一个context
     * @param distributeTaskBO
     * @param next
     * @return
     */
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper distributeTaskBO, StrategyContext next, LoadBalanceStrategy loadBalanceStrategy);

}

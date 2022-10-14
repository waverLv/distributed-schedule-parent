package com.lv.distributed.api;

import com.lv.distributed.annotation.SPI;
import com.lv.distributed.bean.DistributeTaskBO;

import java.net.UnknownHostException;

/**
 * 负载均衡接口
 */
@SPI
public interface LoadBalanceStrategy extends SupportStrategy {

    public void balance(DistributeTaskBO distributeTaskBO);

    public void choose(DistributeTaskBO distributeTaskBO);


}

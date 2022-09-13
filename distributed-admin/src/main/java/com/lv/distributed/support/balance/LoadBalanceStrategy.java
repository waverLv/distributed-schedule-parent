package com.lv.distributed.support.balance;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.api.InvokeStrategy;

/**
 * 负载均衡接口
 */
public interface LoadBalanceStrategy {


    public void balance(DistributeTaskBO distributeTaskBO);


}

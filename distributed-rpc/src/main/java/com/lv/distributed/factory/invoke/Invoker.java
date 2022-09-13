package com.lv.distributed.factory.invoke;

import com.lv.distributed.bean.DistributeRequest;

/**
 * 客户端执行器
 */
public interface Invoker {

    public void invoke(DistributeRequest request);
}

package com.lv.distributed.factory.invoke;

import com.lv.distributed.bean.DistributeRequest;

/**
 * 客户端执行器
 */
public interface Invoker {
    /**
     * 调用方法
     * @param request
     */
    public void invoke(DistributeRequest request);

    /**
     * 回调方法，对应一次调用
     * @param request
     */
    public void callback(DistributeRequest request);
}

package com.lv.distributed.support.context;

import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * @ProjectName: StrategyContext
 * @Author: lvminghui
 * @Description: 策略容器接口
 * @Date: 2022/11/3 16:39
 * @Version: 1.0
 */
public interface StrategyContext {
    /**
     * 同步调用方法
     * @param wrapper
     * @return
     */
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper);

    /**
     * 异步调用方法
     * @param wrapper
     * @param <T>
     * @return
     */
    public <T> CompletableFuture<T> asyncInvoke(DistributeTaskBOWrapper wrapper);

    public StrategyContext next();

    public StrategyContext head();
}

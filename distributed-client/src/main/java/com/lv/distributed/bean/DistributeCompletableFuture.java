package com.lv.distributed.bean;

import com.lv.distributed.wheel.SystemTimer;
import com.lv.distributed.wheel.Timer;
import com.lv.distributed.wheel.TimerTask;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: DistributeCompletableFuture
 * @Author: lvminghui
 * @Description: 分布式future
 * @Date: 2022/11/14 15:26
 * @Version: 1.0
 */
public class DistributeCompletableFuture extends CompletableFuture {

    private static final Map<Long, DistributeCompletableFuture> FUTURES = new ConcurrentHashMap<>();
    private static final Map<Long, ChannelHandlerContext> CHANNELS = new ConcurrentHashMap<>();
    private static final Timer TIMEOUT_TIMER = new SystemTimer("timeout-pool",100l,512);
    private DistributeTask task;
    private Integer timeout;
    private Long requestId;

    private DistributeCompletableFuture(DistributeTask task, Integer timeout) {
        this.task = task;
        this.requestId = task.requestBody().getRequestId();
        this.timeout = timeout > 0 ? timeout : 1000;
        FUTURES.put(this.requestId,this);
        CHANNELS.put(this.requestId,task.getCtx());
    }


    public static DistributeCompletableFuture newFuture(DistributeTask task, Integer timeout){
        DistributeCompletableFuture future = new DistributeCompletableFuture(task,timeout);
        timeoutCheck(future);
        return future;
    }

    public static DistributeCompletableFuture getFuture(long id) {
        return FUTURES.get(id);
    }

    private static void timeoutCheck(DistributeCompletableFuture future){
        final  DistributeCompletableFuture  timeoutFuture = future;
        TIMEOUT_TIMER.add(new TimerTask(timeoutFuture.timeout) {
            @Override
            public void run() {
                DistributeCompletableFuture future = DistributeCompletableFuture.getFuture(timeoutFuture.requestId);
                if (future != null && !future.isDone()) {
                    notifyTimeout(future);
                    clear(future);
                }
            }
        });
    }

    private static void notifyTimeout(DistributeCompletableFuture future){
        DistributeTaskResponseWrapper wrapper = new DistributeTaskResponseWrapper();
        DistributeTaskResponse response = new DistributeTaskResponse();
        response.setException(new RuntimeException("服务端调用远程:"+remoteAddress(future)+
                "服务："+future.task.requestBody().getMethodBean()+
                "."+future.task.requestBody().getMethodName()+
                "超时："+future.timeout+"毫秒"));
        future.complete(wrapper);

    }

    private static String remoteAddress(DistributeCompletableFuture future){
        InetSocketAddress socketAddress = (InetSocketAddress) future.task.getCtx().channel().remoteAddress();
        return socketAddress.getHostString();
    }

    private static void clear(DistributeCompletableFuture future){
        FUTURES.remove(future.requestId);
        CHANNELS.remove(future.requestId);
    }



}

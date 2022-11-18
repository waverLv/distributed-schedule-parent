package com.lv.distributed.bean;

import com.lv.distributed.wheel.SystemTimer;
import com.lv.distributed.wheel.Timer;
import com.lv.distributed.wheel.TimerTask;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.WeakHashMap;
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
    }


    public static DistributeCompletableFuture newFuture(DistributeTask task, Integer timeout){
        DistributeCompletableFuture future = new DistributeCompletableFuture(task,timeout);
        timeoutCheck(future);
        return future;
    }
    public static DistributeCompletableFuture newFuture(DistributeTaskBOWrapper wrapper){
        DistributeTask distributeTask = newTask(wrapper);
        executorContext.submit(distributeTask);
        DistributeCompletableFuture future = new DistributeCompletableFuture(task,timeout);
        timeoutCheck(future);
        return future;
    }

    public static DistributeCompletableFuture getFuture(long id) {
        return FUTURES.get(id);
    }

    private static void timeoutCheck(DistributeCompletableFuture future){
        final  DistributeCompletableFuture  timeOutFuture = future;
        TIMEOUT_TIMER.add(new TimerTask(timeOutFuture.timeout) {
            @Override
            public void run() {
                DistributeCompletableFuture future = DistributeCompletableFuture.getFuture(timeOutFuture.requestId);
                if (future != null && !future.isDone()) {
                    notifyTimeout(future);
                }
            }
        });
    }

    private static void notifyTimeout(DistributeCompletableFuture future){
        DistributeTaskResponseWrapper wrapper = new DistributeTaskResponseWrapper();
        DistributeTaskResponse response = new DistributeTaskResponse();
        response.setException(new RuntimeException("服务端调用远程:"+future.task.requestBody().));
    }


    private DistributeTask newTask(DistributeTaskBOWrapper wrapper){
        DistributeRequestBody body = requestBody(wrapper);
        return distributeTaskFactory.newTask(body, wrapper.isStart(), wrapper.getCtx());

    }

    private DistributeRequestBody requestBody(DistributeTaskBOWrapper wrapper){
        DistributeRequestBody body = new DistributeRequestBody();
        BeanUtils.copyProperties(wrapper,body);
        return body;
    }
}

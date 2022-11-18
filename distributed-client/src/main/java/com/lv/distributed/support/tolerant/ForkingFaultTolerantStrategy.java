package com.lv.distributed.support.tolerant;

import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.context.StrategyContext;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ProjectName: ForkingFaultTolerantStrategy
 * @Author: lvminghui
 * @Description: 并行调用策略
 * @Date: 2022/11/4 15:42
 * @Version: 1.0
 */
public class ForkingFaultTolerantStrategy extends AbstractFaultTolerantStrategy {
    private static  final Logger LOGGER  = LoggerFactory.getLogger(ForkingFaultTolerantStrategy.class);

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Integer TIMEOUT = 3000;
    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper, StrategyContext next, LoadBalanceStrategy loadBalanceStrategy) {
        int size = wrapper.getAddressList().size();
        List<ChannelHandlerContext> selectedList = new ArrayList<>();
        for(int i=0; i<size; i++){
            select(wrapper,loadBalanceStrategy,selectedList);
            if(!selectedList.contains(wrapper.getCtx())){
                selectedList.add(wrapper.getCtx());
            }
        }
        BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        for(int k=0; k< selectedList.size(); k++){
            final int index = k;
            EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    wrapper.setCtx(selectedList.get(index));
                    try{
                        queue.offer(doInvoke(wrapper,next));
                    }catch (Throwable ex){
                        queue.offer(ex);
                    }

                }
            });
        }
        Object result = null;
        try {
            result = queue.poll(TIMEOUT, TimeUnit.MILLISECONDS);
            if(result instanceof Throwable){
                //TODO 扩展异常信息
                throw new RuntimeException();
            }
            return (DistributeTaskResponseWrapper) result;
        } catch (InterruptedException e) {
            //TODO 扩展异常信息
            throw new RuntimeException();
        }

    }
}

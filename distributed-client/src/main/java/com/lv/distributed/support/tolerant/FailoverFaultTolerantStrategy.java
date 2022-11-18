package com.lv.distributed.support.tolerant;

import com.lv.distributed.annotation.Activate;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.context.StrategyContext;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Activate
public class FailoverFaultTolerantStrategy extends AbstractFaultTolerantStrategy{
    private static  final Logger LOGGER  = LoggerFactory.getLogger(FailoverFaultTolerantStrategy.class);
    //重试次数
    private final Integer RETRY_COUTER = 3;

    public FailoverFaultTolerantStrategy(){
        System.out.println("通过扩展点获取到了");
    }

    /**
     * 1、调用负载策略，指定调用服务器
     * 2、调用下一个context
     * @param wrapper
     * @param next
     * @return
     */
    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper, StrategyContext next, LoadBalanceStrategy loadBalanceStrategy) {

            List<ChannelHandlerContext> invokedList = new ArrayList<>();
            for(int i=0; i<RETRY_COUTER; i++){
                try{
                    select(wrapper,loadBalanceStrategy,invokedList);
                    invokedList.add(wrapper.getCtx());
                    return  doInvoke(wrapper,next);
                }catch (Throwable e){

                }
            }
            //TODO 扩展自定义异常
            throw new RuntimeException();

    }

}

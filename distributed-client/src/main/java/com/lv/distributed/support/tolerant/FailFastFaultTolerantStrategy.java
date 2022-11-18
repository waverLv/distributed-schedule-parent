package com.lv.distributed.support.tolerant;

import com.lv.distributed.annotation.Activate;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.context.StrategyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate
public class FailFastFaultTolerantStrategy extends AbstractFaultTolerantStrategy{
    private static  final Logger LOGGER  = LoggerFactory.getLogger(FailFastFaultTolerantStrategy.class);

    public FailFastFaultTolerantStrategy(){
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
        try{
            select(wrapper,  loadBalanceStrategy,null);
            return doInvoke(wrapper,next);
        }catch (Throwable e){
            LOGGER.error("Failfast策略调用服务器:"+wrapper.getCtx().channel().remoteAddress()+
                    "上的服务："+wrapper.getMethodBean()+
                    ",定时任务："+wrapper.getMethodName()+
                    "出现异常，最后的异常信息："+e.getMessage()+";",e);
            throw  e;
        }
    }
}

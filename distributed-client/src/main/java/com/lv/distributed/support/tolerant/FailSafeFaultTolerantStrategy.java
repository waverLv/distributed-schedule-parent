package com.lv.distributed.support.tolerant;

import com.lv.distributed.annotation.Activate;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.context.StrategyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate
public class FailSafeFaultTolerantStrategy extends AbstractFaultTolerantStrategy{
    private static  final Logger LOGGER  = LoggerFactory.getLogger(FailSafeFaultTolerantStrategy.class);

    public FailSafeFaultTolerantStrategy(){
        System.out.println("通过扩展点获取到了");
    }

    @Override
    public DistributeTaskResponseWrapper invoke(DistributeTaskBOWrapper wrapper, StrategyContext next, LoadBalanceStrategy loadBalanceStrategy) {
        try{
            select(wrapper,loadBalanceStrategy,null);
            return doInvoke(wrapper,next);
        }catch (Throwable e){
            LOGGER.error("Failsafe策略忽略异常:"+e.getMessage(),e);
            return new DistributeTaskResponseWrapper();
        }
    }


}

package com.lv.distributed.support.pipeline;

import com.lv.distributed.support.RouterInvokeStrategyContext;
import com.lv.distributed.support.balance.LoadBalanceStrategyChooser;
import com.lv.distributed.support.fault_tolerant.FaultTolerantStrategyChooser;
import org.springframework.stereotype.Component;

@Component
public class DefaultInvokeStrategyPipelineFactory implements InvokeStrategyPipelineFactory {

    private FaultTolerantStrategyChooser faultTolerantStrategyChooser;
    private LoadBalanceStrategyChooser loadBalanceStrategyChooser;
    private RouterInvokeStrategyContext routerInvokeStrategyContext;

    public DefaultInvokeStrategyPipelineFactory(){
        this(new FaultTolerantStrategyChooser(), new LoadBalanceStrategyChooser());
    }

    public DefaultInvokeStrategyPipelineFactory(FaultTolerantStrategyChooser faultTolerantStrategyChooser, LoadBalanceStrategyChooser loadBalanceStrategyChooser){
        this.faultTolerantStrategyChooser = faultTolerantStrategyChooser;
        this.loadBalanceStrategyChooser = loadBalanceStrategyChooser;
    }
    @Override
    public InvokeStrategyPipeline newInstance( ) {
        DefaultInvokeStrategyPipeline pipeline = new DefaultInvokeStrategyPipeline();
        pipeline.addLast(this.faultTolerantStrategyChooser);
        pipeline.addLast(this.loadBalanceStrategyChooser);
        return pipeline;
    }

}

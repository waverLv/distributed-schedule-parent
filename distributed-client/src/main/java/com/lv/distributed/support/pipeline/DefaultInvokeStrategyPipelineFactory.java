package com.lv.distributed.support.pipeline;

import com.lv.distributed.factory.SpiExtensionFactory;
import com.lv.distributed.support.balance.LoadBalanceStrategy;
import com.lv.distributed.support.chooser.FaultTolerantStrategyChooser;
import com.lv.distributed.support.chooser.LoadBalanceStrategyChooser;
import com.lv.distributed.support.context.FaultTolerantInvokeStrategyContext;
import com.lv.distributed.support.context.RouterInvokeStrategyContext;
import com.lv.distributed.support.tolerant.FaultTolerantStrategy;
import org.springframework.stereotype.Component;

@Component
public class DefaultInvokeStrategyPipelineFactory implements InvokeStrategyPipelineFactory {

    private FaultTolerantStrategyChooser faultTolerantStrategyChooser;
    private LoadBalanceStrategyChooser loadBalanceStrategyChooser;
    private RouterInvokeStrategyContext routerInvokeStrategyContext;

    public DefaultInvokeStrategyPipelineFactory(){
        this(new FaultTolerantStrategyChooser(new SpiExtensionFactory()), new LoadBalanceStrategyChooser(new SpiExtensionFactory()));
    }

    public DefaultInvokeStrategyPipelineFactory(FaultTolerantStrategyChooser faultTolerantStrategyChooser, LoadBalanceStrategyChooser loadBalanceStrategyChooser){
        this.faultTolerantStrategyChooser = faultTolerantStrategyChooser;
        this.loadBalanceStrategyChooser = loadBalanceStrategyChooser;
    }
    @Override
    public InvokeStrategyPipeline newInstance( ) {
        DefaultInvokeStrategyPipeline pipeline = new DefaultInvokeStrategyPipeline();
        pipeline.addLast(new FaultTolerantInvokeStrategyContext<FaultTolerantStrategy,LoadBalanceStrategy>(this.faultTolerantStrategyChooser,this.loadBalanceStrategyChooser));
        return pipeline;
    }
}

package com.lv.distributed.support.pipeline;

import com.lv.distributed.api.FaultTolerantStrategy;
import com.lv.distributed.api.LoadBalanceStrategy;
import com.lv.distributed.factory.SpiExtensionFactory;
import com.lv.distributed.support.balance.RoundRobinLoadBalanceStrategy;
import com.lv.distributed.support.context.FaultTolerantInvokeStrategyContext;
import com.lv.distributed.support.context.LoadBalanceInvokeStrategyContext;
import com.lv.distributed.support.context.RouterInvokeStrategyContext;
import com.lv.distributed.support.chooser.LoadBalanceStrategyChooser;
import com.lv.distributed.support.tolerant.FailoverFaultTolerantStrategy;
import com.lv.distributed.support.chooser.FaultTolerantStrategyChooser;
import org.springframework.stereotype.Component;

@Component
public class DefaultInvokeStrategyPipelineFactory implements InvokeStrategyPipelineFactory {

    private FaultTolerantStrategyChooser faultTolerantStrategyChooser;
    private LoadBalanceStrategyChooser loadBalanceStrategyChooser;
    private RouterInvokeStrategyContext routerInvokeStrategyContext;

    public DefaultInvokeStrategyPipelineFactory(){
        this(new FaultTolerantStrategyChooser<FaultTolerantStrategy>(new SpiExtensionFactory()), new LoadBalanceStrategyChooser<LoadBalanceStrategy>(new SpiExtensionFactory()));
    }

    public DefaultInvokeStrategyPipelineFactory(FaultTolerantStrategyChooser faultTolerantStrategyChooser, LoadBalanceStrategyChooser loadBalanceStrategyChooser){
        this.faultTolerantStrategyChooser = faultTolerantStrategyChooser;
        this.loadBalanceStrategyChooser = loadBalanceStrategyChooser;
        this.addFaultTolerantStrategy();
        this.addLoadBalanceStrategy();
    }
    @Override
    public InvokeStrategyPipeline newInstance( ) {
        DefaultInvokeStrategyPipeline pipeline = new DefaultInvokeStrategyPipeline();
        pipeline.addLast(new FaultTolerantInvokeStrategyContext<FaultTolerantStrategy>(this.faultTolerantStrategyChooser));
        pipeline.addLast(new LoadBalanceInvokeStrategyContext<LoadBalanceStrategy>(this.loadBalanceStrategyChooser));
        return pipeline;
    }
    private void addFaultTolerantStrategy(){
        FailoverFaultTolerantStrategy failoverFaultTolerantStrategy = new FailoverFaultTolerantStrategy();
        this.faultTolerantStrategyChooser
                .add("default",failoverFaultTolerantStrategy)
                .add("failover", failoverFaultTolerantStrategy);
    }
    private void addLoadBalanceStrategy(){
        RoundRobinLoadBalanceStrategy roundRobinLoadBalanceStrategy = new RoundRobinLoadBalanceStrategy();
        this.loadBalanceStrategyChooser
                .add("default",roundRobinLoadBalanceStrategy)
                .add("roundRobin", roundRobinLoadBalanceStrategy);
    }

}

package com.lv.distributed.bootstrap;

import com.lv.distributed.bean.ScheduleConfig;
import com.lv.distributed.client.NettyClient;
import com.lv.distributed.event.dispatch.EventDispatch;
import com.lv.distributed.factory.bean.AbstractScheduleBeanFactory;
import com.lv.distributed.factory.bean.AnnotationScheduleBeanFactory;
import com.lv.distributed.factory.bean.ScheduleBeanFactory;
import com.lv.distributed.factory.invoke.Invoker;
import com.lv.distributed.factory.method.ScheduleMethodFactory;
import com.lv.distributed.factory.scanner.PackageScannerContext;
import org.junit.Assert;

/**
 * @ProjectName: DistributeClientBootstrap
 * @Author: lvminghui
 * @Description: 分布式定时任务客户端启动辅助类
 * @Date: 2022/9/6 15:09
 * @Version: 1.0
 */
public class DistributeSchduleClientBootstrap {

    private NettyClient nettyClient;
    private ScheduleBeanFactory scheduleBeanFactory;
    private PackageScannerContext packageScannerContext;
    private EventDispatch eventDispatch;
    private ScheduleMethodFactory scheduleMethodFactory;
    private Invoker invoker;
    private ScheduleConfig scheduleConfig;


    private DistributeSchduleClientBootstrap scheduleBeanFactory(ScheduleBeanFactory scheduleBeanFactory){
        Assert.assertNotNull("scheduleBeanFactory不能为空",scheduleBeanFactory);
        this.scheduleBeanFactory = scheduleBeanFactory;
        return this;
    }

    public DistributeSchduleClientBootstrap packageScannerContext(PackageScannerContext packageScannerContext){
        this.packageScannerContext = packageScannerContext;
        return this;
    }
    public DistributeSchduleClientBootstrap eventDispatch(EventDispatch eventDispatch){
        this.eventDispatch = eventDispatch;
        return this;
    }
    public DistributeSchduleClientBootstrap scheduleMethodFactory(ScheduleMethodFactory scheduleMethodFactory){
        this.scheduleMethodFactory = scheduleMethodFactory;
        return this;
    }
    public DistributeSchduleClientBootstrap invoker(Invoker invoker){
        this.invoker = invoker;
        return this;
    }

    public DistributeSchduleClientBootstrap scheduleConfig(ScheduleConfig scheduleConfig){
        this.scheduleConfig = scheduleConfig;
        return this;
    }

    public void start(){
        Assert.assertNotNull("scheduleMethodFactory不能为空",scheduleMethodFactory);
        Assert.assertNotNull("packageScannerContext不能为空",packageScannerContext);
        Assert.assertNotNull("eventDispatch不能为空",eventDispatch);
        Assert.assertNotNull("invoker不能为空",invoker);
        Assert.assertNotNull("scheduleConfig不能为空",scheduleConfig);
        if (null == scheduleBeanFactory){
            scheduleBeanFactory = new AnnotationScheduleBeanFactory(packageScannerContext,eventDispatch,scheduleMethodFactory,scheduleConfig);
        }
        scheduleBeanFactory.start();
        initNettyClient();
    }

    private void initNettyClient(){
        AbstractScheduleBeanFactory scheduleBeanFactory = (AbstractScheduleBeanFactory) this.scheduleBeanFactory;
        nettyClient = new NettyClient(scheduleBeanFactory.generateRequestBody(),invoker);
        nettyClient.start();
    }

}

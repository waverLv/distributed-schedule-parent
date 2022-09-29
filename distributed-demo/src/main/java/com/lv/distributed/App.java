package com.lv.distributed;

import com.lv.distributed.bean.ScheduleConfig;
import com.lv.distributed.bean.ServerProperty;
import com.lv.distributed.bootstrap.DistributeSchduleClientBootstrap;
import com.lv.distributed.client.NettyClient;
import com.lv.distributed.common.NettyConstant;
import com.lv.distributed.event.dispatch.AbstractEventDispatch;
import com.lv.distributed.event.dispatch.MulticasterEventDispatch;
import com.lv.distributed.event.event.AllBeansRegisterEvent;
import com.lv.distributed.event.listener.AllBeansRegisterListener;
import com.lv.distributed.factory.method.DefaultScheduleMethodFactory;
import com.lv.distributed.factory.scanner.DefaultPackageScannerContext;
import com.lv.distributed.invoke.DefaultInvoker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App{
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        DistributeSchduleClientBootstrap bootstrap = new DistributeSchduleClientBootstrap();
        DefaultScheduleMethodFactory smf = new DefaultScheduleMethodFactory();
        bootstrap.packageScannerContext(new DefaultPackageScannerContext("com.lv.distributed"))
                .eventDispatch(new MulticasterEventDispatch(null,
                        new MulticasterEventDispatch.EventListenerContext().registerListener(new AllBeansRegisterListener())))
                .scheduleMethodFactory(smf)
                .invoker(new DefaultInvoker(smf))
                .scheduleConfig(initConfig());
        bootstrap.start();
    }

    private static ScheduleConfig initConfig(){

        List<ServerProperty> serverProperties = new ArrayList<>();
        serverProperties.add(new ServerProperty(NettyConstant.REMOTEIP,NettyConstant.REMOTE_PORT));
        ScheduleConfig config = new ScheduleConfig();
        config.applicationName("samples");
        config.serverProperties(serverProperties);
        return config;
    }

}

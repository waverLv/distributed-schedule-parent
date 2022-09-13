package com.lv.distributed.factory.bean;

import com.lv.distributed.bean.DistributeRequestBody;
import com.lv.distributed.event.dispatch.EventDispatch;
import com.lv.distributed.event.event.AllBeansRegisterEvent;
import com.lv.distributed.factory.method.MethodDefinition;
import com.lv.distributed.factory.method.ScheduleMethodFactory;
import com.lv.distributed.factory.scanner.PackageScannerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: AnnotationScheduleBeanFactory
 * @Author: lvminghui
 * @Description: 注解bean容器
 * @Date: 2022/8/12 14:05
 * @Version: 1.0
 */
public class AnnotationScheduleBeanFactory extends AbstractScheduleBeanFactory {

    public AnnotationScheduleBeanFactory(PackageScannerContext scannerContext, EventDispatch eventDispatch, ScheduleMethodFactory scheduleMethodFactory) {
        super(scannerContext,eventDispatch,scheduleMethodFactory);
    }


    public void start(){
        registerBeans();
        notifyAllBeansRegisterFinished();
    }

    @Override
    public void notifyBeanRegisterFinished(BeanDefinition beanDefinition) {

    }

    @Override
    public void notifyAllBeansRegisterFinished() {
        AllBeansRegisterEvent event = new AllBeansRegisterEvent(generateRequestBody());
        eventDispatch.dispatch(event);
    }



}

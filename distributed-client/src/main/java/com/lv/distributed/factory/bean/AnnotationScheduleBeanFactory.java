package com.lv.distributed.factory.bean;

import com.lv.distributed.bean.ScheduleConfig;
import com.lv.distributed.event.dispatch.EventDispatch;
import com.lv.distributed.event.event.AllBeansRegisterEvent;
import com.lv.distributed.event.event.BeanRegisterEvent;
import com.lv.distributed.factory.method.ScheduleMethodFactory;
import com.lv.distributed.factory.scanner.PackageScannerContext;

/**
 * @ProjectName: AnnotationScheduleBeanFactory
 * @Author: lvminghui
 * @Description: 注解bean容器
 * @Date: 2022/8/12 14:05
 * @Version: 1.0
 */
public class AnnotationScheduleBeanFactory extends AbstractScheduleBeanFactory {

    public AnnotationScheduleBeanFactory(PackageScannerContext scannerContext, EventDispatch eventDispatch, ScheduleMethodFactory scheduleMethodFactory) {
        this(scannerContext,eventDispatch,scheduleMethodFactory,null);
    }
    public AnnotationScheduleBeanFactory(PackageScannerContext scannerContext, EventDispatch eventDispatch, ScheduleMethodFactory scheduleMethodFactory, ScheduleConfig scheduleConfig) {
        super(scannerContext,eventDispatch,scheduleMethodFactory,scheduleConfig);
    }


    public void start(){
        registerBeans();
        notifyAllBeansRegisterFinished();
    }

    @Override
    public void notifyBeanRegisterFinished(BeanDefinition beanDefinition) {
        BeanRegisterEvent event = new BeanRegisterEvent(beanDefinition);
        eventDispatch.dispatch(event);
    }

    @Override
    public void notifyAllBeansRegisterFinished() {
        AllBeansRegisterEvent event = new AllBeansRegisterEvent(generateRequestBody());
        eventDispatch.dispatch(event);
    }



}

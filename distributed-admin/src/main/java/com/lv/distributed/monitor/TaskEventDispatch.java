package com.lv.distributed.monitor;

import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: TaskDispatch
 * @Author: lvminghui
 * @Description: 任务分发器
 * @Date: 2022/9/21 14:39
 * @Version: 1.0
 */
@Component
public class TaskEventDispatch implements EventDispatch {

    private ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(ApplicationEvent event){
        applicationEventPublisher.publishEvent(event);
    }
}

package com.lv.distributed.monitor;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisherAware;

public interface EventDispatch extends ApplicationEventPublisherAware {
    public void publish(ApplicationEvent applicationEvent);
}

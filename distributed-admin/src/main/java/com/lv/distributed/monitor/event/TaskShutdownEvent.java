package com.lv.distributed.monitor.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ProjectName: TaskStartEvent
 * @Author: lvminghui
 * @Description: 任务关闭事件
 * @Date: 2022/9/21 14:25
 * @Version: 1.0
 */
public class TaskShutdownEvent extends ApplicationEvent {
    public TaskShutdownEvent(Object source) {
        super(source);
    }
}

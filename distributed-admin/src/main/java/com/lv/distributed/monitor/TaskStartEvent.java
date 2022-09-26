package com.lv.distributed.monitor;

import org.springframework.context.ApplicationEvent;

/**
 * @ProjectName: TaskStartEvent
 * @Author: lvminghui
 * @Description: 任务启动事件
 * @Date: 2022/9/21 14:25
 * @Version: 1.0
 */
public class TaskStartEvent extends ApplicationEvent {
    public TaskStartEvent(Object source) {
        super(source);
    }
}

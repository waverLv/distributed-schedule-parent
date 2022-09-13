package com.lv.distributed.event.event;

import java.io.Serializable;

/**
 * @ProjectName: EventObject
 * @Author: lvminghui
 * @Description: 事件超类
 * @Date: 2022/9/5 11:26
 * @Version: 1.0
 */
public class EventObject implements Serializable {
    protected Object source;
    private Long timestampe = System.currentTimeMillis();

    public EventObject(Object source){
        this.source = source;
    }

    public Object getSource() {
        return source;
    }

    public Long getTimestampe() {
        return timestampe;
    }


    @Override
    public String toString() {
        return "EventObject{" +
                "source=" + source +
                ", timestampe=" + timestampe +
                '}';
    }
}

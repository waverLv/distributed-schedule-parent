package com.lv.distributed.event.dispatch;

import com.lv.distributed.event.event.EventObject;

public interface EventDispatch {
    /**
     * 事件分发
     * @param eventObject
     */
    public void dispatch(EventObject eventObject);
}

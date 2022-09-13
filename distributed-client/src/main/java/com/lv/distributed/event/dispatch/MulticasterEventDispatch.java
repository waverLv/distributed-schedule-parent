package com.lv.distributed.event.dispatch;

import java.util.concurrent.ExecutorService;

/**
 * @ProjectName: MulticasterEventDispatch
 * @Author: lvminghui
 * @Description: 广播事件派发器
 * @Date: 2022/9/5 15:54
 * @Version: 1.0
 */
public class MulticasterEventDispatch extends  AbstractEventDispatch {

    public MulticasterEventDispatch(ExecutorService executorService, EventListenerContext eventListenerContext) {
        super(executorService, eventListenerContext);
    }
}

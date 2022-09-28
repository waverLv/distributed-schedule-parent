package com.lv.distributed.util;

import com.lv.distributed.wheel.TimerTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: TimerTaskEntryContext
 * @Author: lvminghui
 * @Description: 数据库任务和内存定时任务容器
 * @Date: 2022/9/28 17:14
 * @Version: 1.0
 */
public class TimerTaskContext {
    private final static Map<Integer, TimerTask> context = new ConcurrentHashMap<>();

    public static TimerTask get(Integer scheduleTaskId){
        return context.get(scheduleTaskId);
    }

    public static void put(Integer scheduleTaskId,TimerTask timerTask){
        context.put(scheduleTaskId,timerTask);
    }
}

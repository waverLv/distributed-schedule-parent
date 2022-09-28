package com.lv.distributed.monitor.listener;

import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.monitor.event.TaskStartEvent;
import com.lv.distributed.service.ExecuteInvokeService;
import com.lv.distributed.util.CronUtil;
import com.lv.distributed.util.TimerTaskContext;
import com.lv.distributed.wheel.SystemTimer;
import com.lv.distributed.wheel.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @ProjectName: TaskStartListener
 * @Author: lvminghui
 * @Description: 任务启动listener
 * @Date: 2022/9/21 14:24
 * @Version: 1.0
 */
@Component
public class TaskStartListener implements ApplicationListener<TaskStartEvent> {

    @Autowired
    SystemTimer systemTimer;
    @Autowired
    ExecuteInvokeService executeInvokeService;

    @Override
    public void onApplicationEvent(TaskStartEvent taskStartEvent) {
        TaskDetailPO  detail = (TaskDetailPO) taskStartEvent.getSource();
        TimerTask timerTask = new TimerTask(taskStartEvent, getDelayTime(detail)) {
            @Override
            public void run() {
                executeInvokeService.invoke(detail);
            }
        };
        systemTimer.add(timerTask);
        TimerTaskContext.put(detail.getId(),timerTask);
    }

    /**
     *获取延迟时间
     * @param detail
     *
     * @return
     */
    private Long getDelayTime(TaskDetailPO detail){
        String cronTab = detail.getCronTab();
        LocalDateTime nextExecuteTime = CronUtil.getNextExecuteTime(cronTab);
        Duration between = Duration.between(LocalDateTime.now(), nextExecuteTime);
        return between.toMillis();
    }


}

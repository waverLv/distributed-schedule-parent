package com.lv.distributed.monitor.listener;

import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.monitor.event.TaskShutdownEvent;
import com.lv.distributed.util.TimerTaskContext;
import com.lv.distributed.wheel.TimerTask;
import org.springframework.context.ApplicationListener;

/**
 * @ProjectName: TaskShutdownListener
 * @Author: lvminghui
 * @Description: 任务关闭listener
 * @Date: 2022/9/28 17:10
 * @Version: 1.0
 */
public class TaskShutdownListener implements ApplicationListener<TaskShutdownEvent> {
    @Override
    public void onApplicationEvent(TaskShutdownEvent taskShutdownEvent) {
        TaskDetailPO  detail = (TaskDetailPO) taskShutdownEvent.getSource();
        TimerTask timerTask = TimerTaskContext.get(detail.getId());
        timerTask.cancel();
    }
}

package com.lv.distributed.service.impl;

import com.lv.distributed.bean.DistributeTask;
import com.lv.distributed.factory.register.ScheduleRegisterContext;
import com.lv.distributed.service.OnceInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnceInvokeServiceImpl implements OnceInvokeService {
    @Autowired(required = false)
    ScheduleRegisterContext scheduleRegisterContext;

    @Override
    public void onceInvoke(Integer scheduleTaskId) {
        List<DistributeTask> taskList = scheduleRegisterContext.getTaskList("null#taskA#a");
        taskList.forEach(task -> {
            task.run();
        });
    }
}

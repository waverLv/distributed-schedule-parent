package com.lv.distributed.service.impl;

import com.google.common.collect.Maps;
import com.lv.distributed.bean.DistributeTask;
import com.lv.distributed.factory.register.ScheduleRegisterContext;
import com.lv.distributed.service.OnceInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OnceInvokeServiceImpl implements OnceInvokeService {
    @Autowired(required = false)
    ScheduleRegisterContext scheduleRegisterContext;

    @Override
    public void onceInvoke(Integer scheduleTaskId) {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(1, "default#taskA#a");
        map.put(2, "default#taskA#b");
        Collection<DistributeTask> taskList = scheduleRegisterContext.getTaskList(map.get(scheduleTaskId));
        taskList.forEach(task -> {
            task.run();
        });
    }
}

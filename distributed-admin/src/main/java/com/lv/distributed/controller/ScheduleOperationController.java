package com.lv.distributed.controller;

import com.lv.distributed.service.TaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleOperationController {
    @Autowired
    TaskDetailService taskDetailService;

    @RequestMapping(value = "start")
    public void startTask(Integer scheduleTaskId){
        taskDetailService.start(scheduleTaskId);
    }

    @RequestMapping(value = "shutdown")
    public void shutdownTask(Integer scheduleTaskId){
        taskDetailService.shutdownTask(scheduleTaskId);
    }

    public void list(){
        
    }
}

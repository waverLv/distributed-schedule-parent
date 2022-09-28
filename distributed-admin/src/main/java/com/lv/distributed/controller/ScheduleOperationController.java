package com.lv.distributed.controller;

import com.lv.distributed.service.TaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleOperationController {
    @Autowired
    TaskDetailService taskDetailService;

    @RequestMapping(value = "start_task")
    public void startTask(Integer scheduleTaskId){
        taskDetailService.start(scheduleTaskId);
    }

    public void shutdownTask(){

    }

    public void list(){
        
    }
}

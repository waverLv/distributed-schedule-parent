package com.lv.distributed.controller;

import com.lv.distributed.service.OnceInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnceInvokeController {

    @Autowired
    OnceInvokeService onceInvokeService;

    @RequestMapping(value = "once_invoke")
    public void onceInvoke(@RequestParam Integer scheduleTaskId){
        onceInvokeService.onceInvoke(scheduleTaskId);
    }
}

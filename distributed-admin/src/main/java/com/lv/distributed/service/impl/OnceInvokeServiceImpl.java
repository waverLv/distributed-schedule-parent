package com.lv.distributed.service.impl;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.factory.register.ScheduleRegisterContext;
import com.lv.distributed.service.ExecuteInvokeService;
import com.lv.distributed.service.OnceInvokeService;
import com.lv.distributed.service.TaskDetailService;
import com.lv.distributed.support.adapter.InvokeStrategyAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnceInvokeServiceImpl implements OnceInvokeService {
    @Autowired
    ExecuteInvokeService executeInvokeService;
    @Autowired
    TaskDetailService taskDetailService;

    @Override
    public void onceInvoke(Integer scheduleTaskId) {
        TaskDetailPO detail = taskDetailService.getDetail(scheduleTaskId);
        DistributeTaskBO  distributeTaskBO = new DistributeTaskBO();
        BeanUtils.copyProperties(detail, distributeTaskBO);
        executeInvokeService.invoke(distributeTaskBO);
    }
}

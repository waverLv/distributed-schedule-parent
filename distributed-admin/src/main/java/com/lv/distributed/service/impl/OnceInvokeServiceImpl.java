package com.lv.distributed.service.impl;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.service.ExecuteInvokeService;
import com.lv.distributed.service.OnceInvokeService;
import com.lv.distributed.service.TaskDetailService;
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
        executeInvokeService.invoke(detail);
    }
}

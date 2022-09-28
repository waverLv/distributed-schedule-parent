package com.lv.distributed.service.impl;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.service.ExecuteInvokeService;
import com.lv.distributed.support.adapter.InvokeStrategyAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecuteInvokeServiceImpl implements ExecuteInvokeService {

    @Autowired
    InvokeStrategyAdapter invokeStrategyAdapter;

    @Override
    public void invoke(TaskDetailPO detail) {
        DistributeTaskBOWrapper distributeTaskBO = new DistributeTaskBOWrapper();
        BeanUtils.copyProperties(detail, distributeTaskBO);
        distributeTaskBO.setApplicationName("samples");
        invokeStrategyAdapter.invoke(distributeTaskBO);
    }
}

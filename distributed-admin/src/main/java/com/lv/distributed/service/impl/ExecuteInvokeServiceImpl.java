package com.lv.distributed.service.impl;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.service.ExecuteInvokeService;
import com.lv.distributed.support.adapter.InvokeStrategyAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecuteInvokeServiceImpl implements ExecuteInvokeService {

    @Autowired
    InvokeStrategyAdapter invokeStrategyAdapter;

    @Override
    public void invoke(DistributeTaskBO distributeTaskBO) {
        invokeStrategyAdapter.invoke(distributeTaskBO);
    }
}

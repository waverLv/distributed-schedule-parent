package com.lv.distributed.service;

import com.lv.distributed.bean.DistributeTaskBO;

/**
 * 任务执行接口
 */
public interface ExecuteInvokeService {

    public void invoke(DistributeTaskBO distributeTaskBO);
}

package com.lv.distributed.service;

import com.lv.distributed.bean.DistributeTaskResponseWrapper;
import com.lv.distributed.bean.TaskDetailPO;

/**
 * 任务执行接口
 */
public interface ExecuteInvokeService {

    public DistributeTaskResponseWrapper invoke(TaskDetailPO detail);
}

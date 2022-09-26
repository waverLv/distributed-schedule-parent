package com.lv.distributed.service;

import com.lv.distributed.bean.TaskExecutorPO;

import java.util.List;

/**
 * 执行器Service
 */
public interface TaskExecutorService {

    void savePO(TaskExecutorPO executorPO);

    List<TaskExecutorPO> getExecutorList(List<Integer> executorIds);
}

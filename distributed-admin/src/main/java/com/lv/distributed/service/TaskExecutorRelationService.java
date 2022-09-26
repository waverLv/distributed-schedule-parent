package com.lv.distributed.service;

import com.lv.distributed.bean.TaskExecutorRelationPO;

import java.util.List;

public interface TaskExecutorRelationService {

    void savePO(TaskExecutorRelationPO taskExecutorRelationPO);

    List<TaskExecutorRelationPO> getTaskExecutorRelations(Integer scheduleTaskId);
}

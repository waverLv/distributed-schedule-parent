package com.lv.distributed.service.mysql;

import com.lv.distributed.bean.mysql.TaskExecutorRelationPO;

import java.util.List;

public interface TaskExecutorRelationService {

    void savePO(TaskExecutorRelationPO taskExecutorRelationPO);

    List<TaskExecutorRelationPO> getTaskExecutorRelations(Integer scheduleTaskId);
}

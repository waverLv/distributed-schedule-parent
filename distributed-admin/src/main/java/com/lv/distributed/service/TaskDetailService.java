package com.lv.distributed.service;

import com.lv.distributed.bean.TaskDetailPO;

import java.util.List;

public interface TaskDetailService {

    void savePO(TaskDetailPO taskDetailPO);

    TaskDetailPO getDetail(Integer scheduleTaskId);

    List<TaskDetailPO> getAllActiveDetails();

    void upsert(TaskDetailPO taskDetailPO);

    void modify(TaskDetailPO taskDetailPO);

    void start(Integer sheduleTaskId);

    void shutdownTask(Integer scheduleTaskId);
}

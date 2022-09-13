package com.lv.distributed.service.mysql;

import com.lv.distributed.bean.mysql.TaskDetailPO;

public interface TaskDetailService {

    void savePO(TaskDetailPO taskDetailPO);

    TaskDetailPO getDetail(Integer scheduleTaskId);
}

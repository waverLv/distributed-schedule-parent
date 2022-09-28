package com.lv.distributed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lv.distributed.bean.TaskExecutorRelationPO;
import com.lv.distributed.dao.TaskExecutorRelationMapper;
import com.lv.distributed.service.TaskExecutorRelationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("dev")
public class TaskExecutorRelationServiceDevImpl  implements TaskExecutorRelationService {
    @Override
    public void savePO(TaskExecutorRelationPO taskExecutorRelationPO) {

//        this.save(taskExecutorRelationPO);
    }

    @Override
    public List<TaskExecutorRelationPO> getTaskExecutorRelations(Integer scheduleTaskId) {
        return null;
    }
}

package com.lv.distributed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lv.distributed.bean.TaskExecutorRelationPO;
import com.lv.distributed.dao.TaskExecutorRelationMapper;
import com.lv.distributed.service.TaskExecutorRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskExecutorRelationServiceImpl extends ServiceImpl<TaskExecutorRelationMapper, TaskExecutorRelationPO> implements TaskExecutorRelationService {
    @Override
    public void savePO(TaskExecutorRelationPO taskExecutorRelationPO) {
        this.save(taskExecutorRelationPO);
    }

    @Override
    public List<TaskExecutorRelationPO> getTaskExecutorRelations(Integer scheduleTaskId) {
        return null;
    }
}

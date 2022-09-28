package com.lv.distributed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lv.distributed.bean.TaskExecutorPO;
import com.lv.distributed.dao.ExecutorMapper;
import com.lv.distributed.service.TaskExecutorService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("dev")
public class ExecutorServiceDevImpl  implements TaskExecutorService {

    @Override
    public void savePO(TaskExecutorPO executorPO) {
//        this.save(executorPO);
    }

    @Override
    public List<TaskExecutorPO> getExecutorList(List<Integer> executorIds) {
        QueryWrapper<TaskExecutorPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("task_id",executorIds);
        queryWrapper.orderByAsc("id");
//        return this.list(queryWrapper);
        return null;
    }
}

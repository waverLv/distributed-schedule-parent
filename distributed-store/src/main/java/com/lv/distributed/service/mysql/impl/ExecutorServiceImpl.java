package com.lv.distributed.service.mysql.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lv.distributed.bean.mysql.ExecutorPO;
import com.lv.distributed.dao.mysql.ExecutorMapper;
import com.lv.distributed.service.mysql.ExecutorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutorServiceImpl extends ServiceImpl<ExecutorMapper, ExecutorPO> implements ExecutorService {

    @Override
    public void savePO(ExecutorPO executorPO) {
        this.save(executorPO);
    }

    @Override
    public List<ExecutorPO> getExecutorList(List<Integer> executorIds) {
        QueryWrapper<ExecutorPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("task_id",executorIds);
        queryWrapper.orderByAsc("id");
        return this.list(queryWrapper);
    }
}

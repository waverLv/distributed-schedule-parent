package com.lv.distributed.service.mysql.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lv.distributed.bean.mysql.TaskDetailPO;
import com.lv.distributed.dao.mysql.TaskDetailMapper;
import com.lv.distributed.service.mysql.TaskDetailService;
import org.springframework.stereotype.Service;

@Service
public class TaskDetailServiceImpl extends ServiceImpl<TaskDetailMapper, TaskDetailPO> implements TaskDetailService {


    /**
     * 存储任务详情
     * @param taskDetailPO
     */
    public void savePO(TaskDetailPO taskDetailPO){
        this.save(taskDetailPO);
    }

    @Override
    public TaskDetailPO getDetail(Integer scheduleTaskId) {
        return this.getById(scheduleTaskId);
    }
}

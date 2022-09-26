package com.lv.distributed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.dao.TaskDetailMapper;
import com.lv.distributed.monitor.TaskEventDispatch;
import com.lv.distributed.monitor.TaskStartEvent;
import com.lv.distributed.service.TaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDetailServiceImpl extends ServiceImpl<TaskDetailMapper, TaskDetailPO> implements TaskDetailService {
    @Autowired
    TaskEventDispatch dispatch;
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

    @Override
    public List<TaskDetailPO> getAllActiveDetails() {
        return this.list(new QueryWrapper<TaskDetailPO>().eq("start",true));
    }

    @Override
    public void upsert(TaskDetailPO taskDetailPO) {

    }

    @Override
    public void modify(TaskDetailPO taskDetailPO) {

    }

    @Override
    public void start(Integer scheduleTaskId) {
        TaskDetailPO detail = new TaskDetailPO();
        detail.setId(scheduleTaskId);
        detail.setStartFlag(true);
        this.updateById(detail);
        dispatch.publish(new TaskStartEvent(detail));
    }

}

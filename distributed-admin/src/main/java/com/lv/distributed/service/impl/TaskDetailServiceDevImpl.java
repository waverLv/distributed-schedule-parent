package com.lv.distributed.service.impl;

import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.monitor.TaskEventDispatch;
import com.lv.distributed.monitor.event.TaskShutdownEvent;
import com.lv.distributed.monitor.event.TaskStartEvent;
import com.lv.distributed.service.TaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("dev")
public class TaskDetailServiceDevImpl  implements TaskDetailService {
    @Autowired
    TaskEventDispatch dispatch;
    /**
     * 存储任务详情
     * @param taskDetailPO
     */
    public void savePO(TaskDetailPO taskDetailPO){
//        this.save(taskDetailPO);
    }

    @Override
    public TaskDetailPO getDetail(Integer scheduleTaskId) {
//        return this.getById(scheduleTaskId);
        TaskDetailPO detail = new TaskDetailPO();
        detail.setMethodBean("taskA");
        detail.setMethodName("a");
        detail.setFaultTolerantName("default");
        detail.setBalanceName("default");
        detail.setId(scheduleTaskId);
        return detail;
    }

    @Override
    public List<TaskDetailPO> getAllActiveDetails() {
//        return this.list(new QueryWrapper<TaskDetailPO>().eq("start",true));
        return null;
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
        detail.setMethodBean("taskA");
        detail.setMethodName("a");
        detail.setFaultTolerantName("default");
        detail.setBalanceName("default");
        detail.setCronTab(" 0 */1 * * * ?");
//        this.updateById(detail);
        dispatch.publish(new TaskStartEvent(detail));
    }

    @Override
    public void shutdownTask(Integer scheduleTaskId) {
        TaskDetailPO detail = new TaskDetailPO();
        detail.setId(scheduleTaskId);
        detail.setStartFlag(false);
//        this.updateById(detail);
        dispatch.publish(new TaskShutdownEvent(detail));
    }

}

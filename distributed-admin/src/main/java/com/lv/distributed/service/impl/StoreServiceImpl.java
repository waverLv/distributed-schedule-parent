package com.lv.distributed.service.impl;

import com.lv.distributed.api.StoreService;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.TaskDetailPO;
import com.lv.distributed.bean.TaskExecutorPO;
import com.lv.distributed.bean.TaskExecutorRelationPO;
import com.lv.distributed.service.TaskDetailService;
import com.lv.distributed.service.TaskExecutorRelationService;
import com.lv.distributed.service.TaskExecutorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StoreServiceImpl implements StoreService {
    @Autowired
    TaskDetailService taskDetailService;
    @Autowired
    TaskExecutorService executorService;
    @Autowired
    TaskExecutorRelationService taskExecutorRelationService;

    @Transactional
    @Override
    public void store(DistributeTaskBO distributeTaskBO) {
        TaskExecutorPO executorPO = createTaskExecutorPO(distributeTaskBO);
        TaskDetailPO taskDetailPO = createTaskDetailPO(distributeTaskBO);
        taskDetailService.savePO(taskDetailPO);
        executorService.savePO(executorPO);
        taskExecutorRelationService.savePO(new TaskExecutorRelationPO(taskDetailPO.getId(),executorPO.getId()));
    }

    private TaskDetailPO createTaskDetailPO(DistributeTaskBO distributeTaskBO){
        TaskDetailPO detail  = new TaskDetailPO();
        detail.setCreateTime(LocalDateTime.now());
        detail.setUpdateTime(LocalDateTime.now());
        detail.setMethodBean(distributeTaskBO.getMethodBean());
        detail.setMethodName(distributeTaskBO.getMethodName());
        detail.setStartFlag(false);
        return detail;
    }

    private TaskExecutorPO createTaskExecutorPO(DistributeTaskBO distributeTaskBO){
        TaskExecutorPO executor = new TaskExecutorPO();
        executor.setApplicationName(distributeTaskBO.getApplicationName());
        executor.setApplyStatus(false);
        executor.setCreateTime(LocalDateTime.now());
        executor.setUpdateTime(LocalDateTime.now());
        return executor;
    }

    @Override
    public DistributeTaskBO get(Integer scheduleTaskId) {
        List<TaskExecutorRelationPO> relations= taskExecutorRelationService.getTaskExecutorRelations(scheduleTaskId);
        List<Integer> executorIds = relations.stream().map(TaskExecutorRelationPO::getExecutorId).collect(Collectors.toList());
        List<TaskExecutorPO> executorList = executorService.getExecutorList(executorIds);
        TaskDetailPO detail = taskDetailService.getDetail(scheduleTaskId);
        return createTaskBO(executorList,detail);
    }

    private DistributeTaskBO createTaskBO(List<TaskExecutorPO> executorList,TaskDetailPO detail){
        DistributeTaskBO taskBO = new DistributeTaskBO();
        try{
            BeanUtils.copyProperties(taskBO,detail);
            List<DistributeTaskBO.SocketAddress> addressList = executorList.stream().map(executorPO -> {
                DistributeTaskBO.SocketAddress address = new DistributeTaskBO.SocketAddress();
                address.setHost(executorPO.getExecutorHost());
                address.setPort(executorPO.getExecutorPort());
                return address;
            }).collect(Collectors.toList());
            taskBO.setAddressList(addressList);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return taskBO;
    }
}

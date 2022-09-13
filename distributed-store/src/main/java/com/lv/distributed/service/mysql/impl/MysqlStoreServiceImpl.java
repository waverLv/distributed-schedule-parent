package com.lv.distributed.service.mysql.impl;

import com.lv.distributed.api.StoreService;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.mysql.ExecutorPO;
import com.lv.distributed.bean.mysql.TaskDetailPO;
import com.lv.distributed.bean.mysql.TaskExecutorRelationPO;
import com.lv.distributed.service.mysql.ExecutorService;
import com.lv.distributed.service.mysql.TaskDetailService;
import com.lv.distributed.service.mysql.TaskExecutorRelationService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MysqlStoreServiceImpl implements StoreService {
    @Autowired
    TaskDetailService taskDetailService;
    @Autowired
    ExecutorService executorService;
    @Autowired
    TaskExecutorRelationService taskExecutorRelationService;

    @Transactional
    @Override
    public void store(DistributeTaskBO distributeTaskBO) {
        ExecutorPO executorPO = createExecutorPO(distributeTaskBO);
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

    private ExecutorPO createExecutorPO(DistributeTaskBO distributeTaskBO){
        ExecutorPO executor = new ExecutorPO();
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
        List<ExecutorPO> executorList = executorService.getExecutorList(executorIds);
        TaskDetailPO detail = taskDetailService.getDetail(scheduleTaskId);
        return createTaskBO(executorList,detail);
    }

    private DistributeTaskBO createTaskBO(List<ExecutorPO> executorList,TaskDetailPO detail){
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

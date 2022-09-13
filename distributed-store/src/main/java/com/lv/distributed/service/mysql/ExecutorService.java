package com.lv.distributed.service.mysql;

import com.lv.distributed.bean.mysql.ExecutorPO;

import java.util.List;

public interface ExecutorService {

    void savePO(ExecutorPO executorPO);

    List<ExecutorPO> getExecutorList(List<Integer> executorIds);
}

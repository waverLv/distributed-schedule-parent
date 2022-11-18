package com.lv.distributed.support;

import com.lv.distributed.bean.DistributeTaskBO;

public interface StoreService {

    public void store(DistributeTaskBO distributeTaskBO);

    public DistributeTaskBO get(Integer scheduleTaskId);
}

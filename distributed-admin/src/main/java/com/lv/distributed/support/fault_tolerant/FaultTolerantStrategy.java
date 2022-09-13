package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

/**
 * 容错接口
 */
public interface FaultTolerantStrategy {

    public void fault(DistributeTaskBO distributeTaskBO);

    public DistributeTaskResponseWrapper fault(DistributeTaskResponseWrapper response);

}

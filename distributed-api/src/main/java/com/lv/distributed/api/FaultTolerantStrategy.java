package com.lv.distributed.api;

import com.lv.distributed.annotation.SPI;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;

/**
 * 容错接口
 */
@SPI
public interface FaultTolerantStrategy extends SupportStrategy {

    public void fault(DistributeTaskBO distributeTaskBO);

    public DistributeTaskResponseWrapper fault(DistributeTaskRequestWrapper requestWrapper);

}

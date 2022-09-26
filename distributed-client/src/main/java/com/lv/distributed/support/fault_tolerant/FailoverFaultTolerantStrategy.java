package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.annotation.Activate;
import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.bean.DistributeTaskResponseWrapper;
@Activate
public class FailoverFaultTolerantStrategy extends AbstractFaultTolerantStrategy{

    public FailoverFaultTolerantStrategy(){
        System.out.println("通过扩展点获取到了");
    }

    @Override
    public void fault(DistributeTaskBO distributeTaskBO) {

    }

    @Override
    public DistributeTaskResponseWrapper fault(DistributeTaskRequestWrapper requestWrapper) {
        return null;
    }
}

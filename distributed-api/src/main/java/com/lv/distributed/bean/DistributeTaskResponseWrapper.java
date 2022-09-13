package com.lv.distributed.bean;

import com.lv.distributed.api.InvokeStrategy;

public class DistributeTaskResponseWrapper {
    private DistributeTaskResponse response;
    private DistributeTaskBO distributeTaskBO;
    private InvokeStrategy invokeStrategy;

    public DistributeTaskResponse getResponse() {
        return response;
    }

    public void setResponse(DistributeTaskResponse response) {
        this.response = response;
    }

    public DistributeTaskBO getDistributeTaskBO() {
        return distributeTaskBO;
    }

    public void setDistributeTaskBO(DistributeTaskBO distributeTaskBO) {
        this.distributeTaskBO = distributeTaskBO;
    }

    public InvokeStrategy getInvokeStrategy() {
        return invokeStrategy;
    }

    public void setInvokeStrategy(InvokeStrategy invokeStrategy) {
        this.invokeStrategy = invokeStrategy;
    }
}

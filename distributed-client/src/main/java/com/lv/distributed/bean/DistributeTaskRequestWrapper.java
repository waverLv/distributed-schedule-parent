package com.lv.distributed.bean;

public class DistributeTaskRequestWrapper {
    private DistributeTaskRequest distributeTaskRequest;
    private DistributeTaskResponseWrapper responseWrapper;

    public DistributeTaskRequest getDistributeTaskRequest() {
        return distributeTaskRequest;
    }

    public void setDistributeTaskRequest(DistributeTaskRequest distributeTaskRequest) {
        this.distributeTaskRequest = distributeTaskRequest;
    }

    public DistributeTaskResponseWrapper getResponseWrapper() {
        return responseWrapper;
    }

    public void setResponseWrapper(DistributeTaskResponseWrapper responseWrapper) {
        this.responseWrapper = responseWrapper;
    }
}

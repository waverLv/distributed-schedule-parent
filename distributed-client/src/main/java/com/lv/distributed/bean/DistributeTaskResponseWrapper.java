package com.lv.distributed.bean;

public class DistributeTaskResponseWrapper {
    private DistributeTaskResponse response;
    private DistributeTaskBOWrapper wrapper;

    public DistributeTaskResponse getResponse() {
        return response;
    }

    public void setResponse(DistributeTaskResponse response) {
        this.response = response;
    }

    public DistributeTaskBOWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(DistributeTaskBOWrapper wrapper) {
        this.wrapper = wrapper;
    }

}

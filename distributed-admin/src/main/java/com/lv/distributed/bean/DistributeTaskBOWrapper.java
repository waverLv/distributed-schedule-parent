package com.lv.distributed.bean;

public class DistributeTaskBOWrapper extends DistributeTaskBO{
    public DistributeTask getDistributeTask() {
        return distributeTask;
    }

    public void setDistributeTask(DistributeTask distributeTask) {
        this.distributeTask = distributeTask;
    }

    private DistributeTask distributeTask;


}

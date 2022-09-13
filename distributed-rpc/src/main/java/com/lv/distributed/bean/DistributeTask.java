package com.lv.distributed.bean;

public interface DistributeTask extends Runnable {


    public String getUniqueTaskName();
    public String getGroupName();
}

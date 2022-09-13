package com.lv.distributed.service;

/**
 * 任务接口
 */
public interface TaskService {

    public void get(Integer taskId);

    public void list();

    public void get(String applicationName);


}

package com.lv.distributed.service;

/**
 * 任务一次性调用接口
 */
public interface OnceInvokeService {


    void onceInvoke(Integer scheduleTaskId);
}

package com.lv.distributed.service;

import com.lv.distributed.annotation.DistributeComponent;
import com.lv.distributed.task.TaskA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @ProjectName: ServiceB
 * @Author: lvminghui
 * @Description: service b
 * @Date: 2022/9/7 15:09
 * @Version: 1.0
 */
@DistributeComponent
public class ServiceB {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceB.class);
    public void b(){
        LOGGER.info("b 方法被调用,当前时间："+ LocalDateTime.now());
    }
}

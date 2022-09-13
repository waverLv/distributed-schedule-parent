package com.lv.distributed.service;

import com.lv.distributed.annotation.DistributeComponent;

/**
 * @ProjectName: ServiceB
 * @Author: lvminghui
 * @Description: service b
 * @Date: 2022/9/7 15:09
 * @Version: 1.0
 */
@DistributeComponent
public class ServiceB {

    public void b(){
        System.out.println("b 方法被调用");
    }
}

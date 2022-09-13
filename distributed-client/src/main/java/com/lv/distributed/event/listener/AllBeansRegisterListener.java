package com.lv.distributed.event.listener;

import com.lv.distributed.event.event.AllBeansRegisterEvent;

/**
 * @ProjectName: AllBeansRegisterListener
 * @Author: lvminghui
 * @Description: bean注册监听器
 * @Date: 2022/9/5 15:19
 * @Version: 1.0
 */
public class AllBeansRegisterListener extends EventListener<AllBeansRegisterEvent> {


    @Override
    public void onEvent(AllBeansRegisterEvent beansRegisterEvent) {
        System.out.println("所有bean都已注册完成");
    }
}

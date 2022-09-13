package com.lv.distributed.event.event;

/**
 * @ProjectName: BeansRegisterEvent
 * @Author: lvminghui
 * @Description: beans注册事件
 * @Date: 2022/9/5 11:22
 * @Version: 1.0
 */
public class AllBeansRegisterEvent extends EventObject {

    public AllBeansRegisterEvent(Object source) {
        super(source);
    }
}

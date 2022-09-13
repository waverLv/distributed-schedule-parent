package com.lv.distributed.event.event;

/**
 * @ProjectName: BeanRegisterEvent
 * @Author: lvminghui
 * @Description: bean注册事件
 * @Date: 2022/9/5 11:22
 * @Version: 1.0
 */
public class BeanRegisterEvent extends EventObject {

    public BeanRegisterEvent(Object source) {
        super(source);
    }
}

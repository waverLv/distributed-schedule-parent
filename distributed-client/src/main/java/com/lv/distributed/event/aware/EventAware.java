package com.lv.distributed.event.aware;

import com.lv.distributed.event.dispatch.EventDispatch;

/**
 * @ProjectName: EventAware
 * @Author: lvminghui
 * @Description: 事件发布环境
 *               只有实现EventAware接口的实现类可注入EventDispatch，进行事件发送
 * @Date: 2022/9/5 15:54
 * @Version: 1.0
 */
public interface EventAware {

    public void setEventDispatch(EventDispatch eventDispatch);
}

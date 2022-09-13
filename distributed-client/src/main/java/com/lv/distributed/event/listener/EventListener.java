package com.lv.distributed.event.listener;

import com.lv.distributed.event.event.EventObject;

import java.lang.reflect.ParameterizedType;

public abstract class EventListener<E extends EventObject> {

    public abstract  void onEvent(E e);

    public  Class getGenericType(){
       return (Class<E>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }
}

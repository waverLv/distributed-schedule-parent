package com.lv.distributed.event.dispatch;

import com.lv.distributed.event.event.EventObject;
import com.lv.distributed.event.listener.EventListener;
import org.junit.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: AbstractEventDispatch
 * @Author: lvminghui
 * @Description: 事件派发器抽象类
 * @Date: 2022/9/5 15:52
 * @Version: 1.0
 */
public abstract class AbstractEventDispatch implements EventDispatch {

    ExecutorService executorService;
    EventListenerContext eventListenerContext;

    public AbstractEventDispatch(ExecutorService executorService,EventListenerContext eventListenerContext){
        Assert.assertNotNull("eventListenerContext不能为空",eventListenerContext);
        this.eventListenerContext = eventListenerContext;
        this.executorService = executorService;
        if(null == executorService){
            this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }

    }


    /**
     * 事件分发
     * @param eventObject
     */
    public void dispatch(EventObject eventObject){
        List<EventListener> eventListeners = eventListenerContext.getEventListeners(eventObject);
        if(null != eventListeners && eventListeners.size() > 0){
            eventListeners.stream().forEach(listener -> {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        listener.onEvent(eventObject);
                    }
                });
            });
        }
    }

    /**
     * 事件监听容器
     */
    public static class EventListenerContext{
        private Map<Class,List<EventListener>> listenerContext =new ConcurrentHashMap<>();
        /**
         * 根据时间类型获取监听器列表
         */
        public List<EventListener> getEventListeners(EventObject eventObject){
            return listenerContext.get(eventObject.getClass());
        }

        public EventListenerContext registerListeners(List<EventListener> eventListeners){
            Assert.assertNotNull("事件监听器列表不能为空",eventListeners);
            eventListeners.parallelStream().forEach(eventListener -> registerListener(eventListener));
            return this;
        }

        /**
         * 注册监听器
         * @param eventListener
         */
        public EventListenerContext registerListener(EventListener eventListener){
            Class genericType = eventListener.getGenericType();
            if(null != genericType){
                List<EventListener> eventListeners = listenerContext.get(genericType);
                if(null == eventListeners){
                    eventListeners = new ArrayList<>();
                }
                eventListeners.add(eventListener);
                listenerContext.put(genericType,eventListeners);
            }
            return this;
        }

        public static <T> Class getGenericType(Object src, int index) {
            if (src == null || index < 0) {
                return null;
            }
            Class aClass = src.getClass();
            Type gSuperclass = aClass.getGenericSuperclass();
            if(null != gSuperclass && !gSuperclass.getTypeName().equals("java.lang.Object")){
                if (gSuperclass instanceof ParameterizedType) {
                    try {
                        ParameterizedType pType = (ParameterizedType) gSuperclass;
                        Type[] types = pType.getActualTypeArguments();
                        if (index < types.length) {
                            Class desClass = (Class<T>) types[index];
                            return desClass;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

    }
}

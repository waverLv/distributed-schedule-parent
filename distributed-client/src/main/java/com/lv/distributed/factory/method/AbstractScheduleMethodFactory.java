package com.lv.distributed.factory.method;

import com.lv.distributed.annotation.DistributeSchedule;
import com.lv.distributed.factory.bean.BeanDefinition;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractScheduleMethodFactory implements ScheduleMethodFactory{

    private Map<String,MethodDefinition> methodDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public MethodDefinition getMethod(String name) {
        return methodDefinitionMap.get(name);
    }

    @Override
    public void register(BeanDefinition beanDefinition) {
        Method[] declaredMethods = beanDefinition.getType().getDeclaredMethods();
        for(Method method : declaredMethods){
           if(method.isAnnotationPresent(DistributeSchedule.class)){
               MethodDefinition methodDefinition = MethodDefinition.instance().beanDefinition(beanDefinition).method(method);
               String methodDefinitionName = generateMethodName(methodDefinition);
               methodDefinitionMap.putIfAbsent(methodDefinitionName,methodDefinition.name(methodDefinitionName));
           }
        }
    }

    @Override
    public void register(Map<String, BeanDefinition> beanDefinitionMap) {
        Iterator<Map.Entry<String, BeanDefinition>> iterator = beanDefinitionMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, BeanDefinition> next = iterator.next();
            register(next.getValue());
        }
    }


    @Override
    public Map<String,MethodDefinition> getAllMethodDefinitions(){
        return this.methodDefinitionMap;
    }

    @Override
    public MethodDefinition method(String methodDefinitionName) {
        return methodDefinitionMap.get(methodDefinitionName);
    }
}

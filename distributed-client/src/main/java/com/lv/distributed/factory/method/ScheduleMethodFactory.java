package com.lv.distributed.factory.method;

import com.lv.distributed.bean.DistributeRequestBody;
import com.lv.distributed.factory.bean.BeanDefinition;

import java.util.Map;

public interface ScheduleMethodFactory {
    /**
     * 根据方法名称查询methodDefinition
     * @param name
     * @return
     */
    public MethodDefinition getMethod(String name);

    /**
     * beanDefinition向MethodFactory注册methodDefinition
     * @param beanDefinition
     */
    public void register(BeanDefinition beanDefinition);

    /**
     * beanDefinitionMap向MethodFactory注册methodDefinition
     * @param beanDefinitionMap
     */
    public void register(Map<String,BeanDefinition> beanDefinitionMap);

    /**
     * methodName生成方法
     * @return
     */
    public String generateMethodName(MethodDefinition methodDefinition);
    /**
     * methodName生成方法
     * @return
     */
    public String generateMethodName(DistributeRequestBody requestBody);

    /**
     * 获取所有methodDefinition
     * @return
     */
    public Map<String,MethodDefinition> getAllMethodDefinitions();

    /**
     * 根据方法定义名称获取方法
     * @param methodDefinitionName
     * @return
     */
    public MethodDefinition method(String methodDefinitionName);
}

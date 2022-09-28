package com.lv.distributed.factory.bean;

import com.lv.distributed.annotation.DistributeComponent;
import com.lv.distributed.annotation.Inject;
import com.lv.distributed.bean.DistributeRequestBody;
import com.lv.distributed.bean.ScheduleConfig;
import com.lv.distributed.event.aware.EventAware;
import com.lv.distributed.event.dispatch.EventDispatch;
import com.lv.distributed.factory.method.MethodDefinition;
import com.lv.distributed.factory.method.ScheduleMethodFactory;
import com.lv.distributed.factory.scanner.PackageScannerContext;
import com.lv.distributed.proxy.CglibDynamicProxyFactory;
import com.lv.distributed.proxy.JdkDynamicProxyFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: AbstractScheduleBeanFactory
 * @Author: lvminghui
 * @Description: bean容器抽象类
 * @Date: 2022/8/12 14:05
 * @Version: 1.0
 */
public abstract class AbstractScheduleBeanFactory  implements ScheduleBeanFactory, EventAware {

    /**
     * key: beanName
     * value: bean 实例
     */
    private Map<String,BeanDefinition> beanMap = new ConcurrentHashMap<>();
    /**
     * 注册 class beanmap
     */
    private Map<Class,Map<String,BeanDefinition>> clazzesMap = new ConcurrentHashMap<>();
    protected PackageScannerContext scannerContext;
    protected EventDispatch eventDispatch;
    protected ScheduleMethodFactory scheduleMethodFactory;
    protected ScheduleConfig scheduleConfig;


    public AbstractScheduleBeanFactory(PackageScannerContext scannerContext,EventDispatch eventDispatch,ScheduleMethodFactory scheduleMethodFactory,ScheduleConfig scheduleConfig){
        Assert.assertNotNull("packageScannerContext不能为空",scannerContext);
        Assert.assertNotNull("eventDispatch不能为空",eventDispatch);
        Assert.assertNotNull("scheduleMethodFactory不能为空",scheduleMethodFactory);
        this.scannerContext = scannerContext;
        this.setEventDispatch(eventDispatch);
        this.scheduleMethodFactory = scheduleMethodFactory;
        this.scheduleConfig = scheduleConfig;
    }

    @Override
    public void setEventDispatch(EventDispatch eventDispatch) {
        this.eventDispatch = eventDispatch;
    }

    @Override
    public Object getBean(String name) {
        return beanMap.get(name).getProxy();
    }

    @Override
    public Map<String,BeanDefinition> getBean(Class classType) {
        return clazzesMap.get(classType);
    }
    @Override
    public void registerBeans(){
        Set<Class<?>> classes = scannerContext.getTypesAnnotationWith(DistributeComponent.class);
        classes.forEach(clazz -> {
            try {
                BeanDefinition beanDefinition = registerBean(clazz);
                notifyBeanRegisterFinished(beanDefinition);
                registerMethod(beanDefinition);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public BeanDefinition registerBean(Class clazz) throws IllegalAccessException, InstantiationException {
        //实例化
        BeanDefinition bd = newInstance(clazz);
        //属性填充
        populate(bd);
        //包装对象
        wrapInstance(bd);
        //放入容器
        store(bd);
        return bd;
    }

    public BeanDefinition newInstance(Class clazz) throws IllegalAccessException, InstantiationException {
        BeanDefinition bd = new BeanDefinition();
        Object instance = clazz.newInstance();
        bd.setBeanName(generateBeanName(instance));
        bd.setType(clazz);
        bd.setTarget(instance);
        bd.setScheduleConfig(scheduleConfig);
        return bd;
    }


    /**
     * 属性填充
     * @param bd
     */
    private void populate(BeanDefinition bd) throws IllegalAccessException, InstantiationException {
        Field[] declaredFields = bd.getTarget().getClass().getDeclaredFields();
        for(int i=0; i<declaredFields.length; i++){
            Field field = declaredFields[i];
            field.setAccessible(true);
            if(field.isAnnotationPresent(Inject.class)){
                String beanName = getBeanName(field);
                BeanDefinition o = beanMap.get(beanName);
                if(null != o){
                    try {
                        field.set(bd.getTarget(),o.getProxy());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else{
                    registerBean(field.getType());
                    i--;
                }
            }
        };
    }

    public void wrapInstance(BeanDefinition bd){
        Class<?> clazz = bd.getType();
        Class<?>[] interfaces = clazz.getInterfaces();
        Object instance;
        if(null == interfaces || interfaces.length == 0){
            instance =  new CglibDynamicProxyFactory().newProxy(bd.getTarget());
        }else{
            instance = new JdkDynamicProxyFactory().newProxy(bd.getTarget());
        }
        bd.setProxy(instance);
    }

    public void store(BeanDefinition bd){
        beanMap.put(bd.getBeanName(),bd);
        Map<String, BeanDefinition> clazzMap = clazzesMap.get(bd.getType());
        if(null == clazzMap){
            clazzMap = new HashMap<>();
            clazzesMap.put(bd.getType(),clazzMap);
        }
        clazzMap.putIfAbsent(bd.getBeanName(),bd);
    }

    /**
     * 获取注入属性别名
     * @param field
     * @return
     */
    private String getBeanName(Field field){
        Inject annotation = field.getAnnotation(Inject.class);
        if(StringUtils.isNotEmpty(annotation.value())){
            return annotation.value();
        }
        return field.getName();
    }

    /**
     * 生成 bean name
     * @param bean
     * @return
     */
    private String generateBeanName(Object bean){
        DistributeComponent component = bean.getClass().getDeclaredAnnotation(DistributeComponent.class);
        if(null != component && StringUtils.isNotEmpty(component.value())){
            return component.value();
        }
        String simpleName = bean.getClass().getSimpleName();
        char[] chars = simpleName.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        String tempBeanName = new String(chars);
        if(tempBeanName.contains("$")){
            tempBeanName = tempBeanName.substring(0,tempBeanName.indexOf("$"));
        }
        return tempBeanName;
    }

    /**
     * 定时任务执行方法注册
     * @param beanDefinition
     */
    public void registerMethod(BeanDefinition beanDefinition){
        scheduleMethodFactory.register(beanDefinition);
    }


    /**
     * 定时任务单个bean注册完成后事件通知
     */
    public abstract void notifyBeanRegisterFinished(BeanDefinition beanDefinition);

    /**
     * 定时任务beans注册完成后事件通知
     */
    public abstract void notifyAllBeansRegisterFinished();

    public List<DistributeRequestBody> generateRequestBody(){
        Map<String, MethodDefinition> allMethodDefinitions = scheduleMethodFactory.getAllMethodDefinitions();
        List<DistributeRequestBody> bodyList = new ArrayList<>();
        for(Map.Entry<String,MethodDefinition> entry : allMethodDefinitions.entrySet()){
            MethodDefinition value = entry.getValue();
            DistributeRequestBody body = new DistributeRequestBody();
            body.setApplicationName(value.getBeanDefinition().getScheduleConfig().getApplicationName());
            body.setMethodBean(value.getBeanDefinition().getBeanName());
            body.setMethodName(value.getMethod().getName());
            body.setMethodParamTypes(value.getMethod().getParameterTypes());
            bodyList.add(body);
        }
        return bodyList;
    }
}

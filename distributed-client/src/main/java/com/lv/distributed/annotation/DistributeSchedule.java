package com.lv.distributed.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(value= RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeSchedule {
    //任务名称
    public String name();
    //任务描述
    public String desc() default "";
    //cron表达式
    public String cron();
    //负载均衡策略
    public String loadBalance() default "default";
    //容错策略
    public String faultTolerant() default  "default";
    //定时任务参数
    public String param() default "";
    //任务注册后是否启动，默认true,注册后立即启动
    public boolean start() default true;
}

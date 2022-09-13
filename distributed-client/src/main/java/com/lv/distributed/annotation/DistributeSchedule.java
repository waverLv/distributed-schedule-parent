package com.lv.distributed.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(value= RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeSchedule {
    public String name();

    public String desc() default "";
}

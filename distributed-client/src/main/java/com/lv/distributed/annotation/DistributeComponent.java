package com.lv.distributed.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(value= RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeComponent {

    public String value() default "";
}

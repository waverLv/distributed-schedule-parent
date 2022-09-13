package com.lv.distributed.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {

    public String name() default "";
    public String value() default "";
}

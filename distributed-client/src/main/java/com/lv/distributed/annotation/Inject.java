package com.lv.distributed.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
    /**
     * beanName
     * @return
     */
    public String value();
}

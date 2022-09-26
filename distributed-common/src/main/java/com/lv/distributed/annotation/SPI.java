package com.lv.distributed.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {

    /**
     * Value string.
     *
     * @return the string
     */
    String value() default "";
}

package com.lv.distributed.annotation;

import java.lang.annotation.*;

/**
 * @ProjectName: Activate
 * @Author: lvminghui
 * @Description: Adding this annotation to a class indicates joining the extension mechanism.
 * @Date: 2022/9/15 14:40
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Activate {

    String value() default "";
}

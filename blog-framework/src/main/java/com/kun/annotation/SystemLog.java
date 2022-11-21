package com.kun.annotation;

import java.lang.annotation.*;

/**
 * @author kun
 * @since 2022-11-21 13:49
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String businessName() default "";
}

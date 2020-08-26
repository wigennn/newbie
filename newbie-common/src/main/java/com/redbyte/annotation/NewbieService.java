package com.redbyte.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@Component
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewbieService {

    Class<?> value();

    String version() default "";
}

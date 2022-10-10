package com.ca.devtest.sv.devtools.annotation.v3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sm632260
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface SSLConfig {
    String keystoreFile() default "";
    String keystorePassword() default "";
    String alias() default "";
    String aliasPassword() default "";
}

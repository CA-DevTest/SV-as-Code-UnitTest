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
public @interface TargetEndpointConfig {
    boolean useSSL() default false;
    String host() default "";
    String port() default "";
    SSLConfig sslConfig() default @SSLConfig();
}

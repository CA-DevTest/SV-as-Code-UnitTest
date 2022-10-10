package com.ca.devtest.sv.devtools.annotation.v3;


import com.ca.devtest.sv.devtools.annotation.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Repeatable;

/**
 * @author sm632260
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Repeatable(DevTestVirtualServicesV3.class)
public @interface DevTestVirtualServiceV3 {
    String serviceName();
    String description () default "Deployed using SV-as-Code";
    String version() default "1.0";
    String groupTag() default "";
    String port() default "";
    String status() default "";
    String basePath() default "/";
    int capacity() default 1;
    int thinkScale() default 100;
    boolean autoRestartEnabled() default true;
    boolean startOnDeployEnabled() default true;
    VirtualServiceV3Type type() default VirtualServiceV3Type.CREATE;
    String workingFolder();
    String inputFile1() default "";
    String inputFile2() default "";
    String dataFile() default "";
    String activeConfig() default "";
    String swaggerurl() default "";
    String ramlurl() default "";
    String wadlurl() default "";
    TransportProtocolConfig transportProtocolConfig() default @TransportProtocolConfig();
    DataProtocolConfig[] dataProtocolsConfig() default @DataProtocolConfig();
    boolean overwriteTxns() default true;
    Parameter[] parameters() default{};
}

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
public @interface TransportProtocolConfig {
    String typeId() default "HTTP";
    boolean useGateway() default true;
    boolean hostHeaderPassThrough() default false;
    TargetEndpointConfig targetEndpoint() default @TargetEndpointConfig();
    RecordingEndpointConfig recordingEndpoint() default @RecordingEndpointConfig();
}

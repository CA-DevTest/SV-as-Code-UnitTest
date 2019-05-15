/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ca.devtest.sv.devtools.services.ExecutionModeType;

/**
 * Define virtual service. <br/>
 * 
 * Attributes are :
 * <ul>
 * <li>serviceName : virtual service name</li>
 * <li>port : listening port (default is '-1')</li>
 * <li>workingFolder : folder of request/response files</li>
 * <li>basePath : virtual service base path ('/' by default)</li>
 * <li>transport : 'HTTP' by default</li>
 * <li>requestDataProtocol : array with request data protocols (empty by
 * default)</li>
 * <li>responseDataProtocol : array with response data protocols (empty by
 * default)</li>
 * <li>parameters : (empty by default)</li>
 * </ul>
 * 
 * @author gaspa03, bboulch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Repeatable(DevTestVirtualServices.class)
public @interface DevTestVirtualService {
	String serviceName();
	int port() default -1;
	String basePath() default "/";
	int capacity() default 1;
	int thinkScale() default 100;
	boolean autoRestartEnabled() default true;
	ExecutionModeType executionMode() default ExecutionModeType.EFFICIENT;
	
	VirtualServiceType type() default VirtualServiceType.RRPAIRS;
	Protocol transport() default @Protocol(ProtocolType.TPH_HTTP );
	Protocol[] requestDataProtocol() default{ } ;
	Protocol[] responseDataProtocol() default{ } ;
	Class preDeployClass() default Object.class;
	String workingFolder();
	Parameter[] parameters() default{};
}



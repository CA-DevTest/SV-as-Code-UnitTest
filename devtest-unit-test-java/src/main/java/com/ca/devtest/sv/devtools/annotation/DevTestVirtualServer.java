/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify DevTest registry to be used for deployement, with differents
 * parameters. <br/>
 * 
 * Attributes are :
 * <ul>
 * <li>registryHost : registry host (default is "localhost")</li>
 * <li>deployServiceToVse : VSE name (default is "VSE")</li>
 * <li>login : login used to connect to registry (default is "svpower")</li>
 * <li>password : password used to connect to registry (default is
 * "svpower")</li>
 * <li>groupName : group name used to prefix virtual services</li>
 * <li>protocol : protocol used to access API</li>
 * </ul>
 * 
 * @author gaspa03, bboulch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DevTestVirtualServer {

	/**
	 * Registry server name. By default 'localhost'.
	 * 
	 * @return registry server name.
	 */
	String registryHost() default "";

	/**
	 * VSE name. By default 'VSE'.
	 * 
	 * @return VSE name
	 */
	String deployServiceToVse() default "";

	/**
	 * Devtest user. By default 'svpower'
	 * 
	 * @return devtest user
	 */
	String login() default "";

	/**
	 * Devtest password. By default 'svpower'
	 * 
	 * @return devtest password
	 */
	String password() default "";

	/**
	 * Group name used to prefix virtual services. Empty by default.
	 * 
	 * @return group name used to prefix virtual services
	 */
	String groupName() default "";

	/**
	 * Protocol to access API. By default 'http'
	 * 
	 * @return protocol to access api
	 */
	String protocol() default "";

}

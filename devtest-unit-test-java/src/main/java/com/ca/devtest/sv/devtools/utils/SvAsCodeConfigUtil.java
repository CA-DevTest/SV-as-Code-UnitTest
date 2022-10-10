package com.ca.devtest.sv.devtools.utils;

import java.io.File;
import com.ca.devtest.sv.devtools.SVasCodeConfigHandler;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang.StringUtils;
import com.ca.devtest.sv.devtools.SVasCodeConfig;

/**
 * Merge Annotation configuration with svascode.properties file
 * 
 * @author gaspa03
 *
 */
public final class SvAsCodeConfigUtil {

	private static SVasCodeConfig CONFIG = ConfigFactory.create(SVasCodeConfig.class, System.getProperties(),
			System.getenv());

	static {
		if((new SVasCodeConfigHandler()).encryptProperties()){
			CONFIG = ConfigFactory.create(SVasCodeConfig.class, System.getProperties(),System.getenv());
		}
	}

	/**
	 * Registry server name. By default 'localhost'.
	 * 
	 * @return registry server name.
	 */
	public static String registryHost(String registryHost) {

		return StringUtils.defaultIfEmpty(registryHost, CONFIG.registryHost());
	}

	/**
	 * @param devTestServer
	 * @return
	 */
	public static String deployServiceToVse(String deployServiceToVse) {
		String vseName = null;
		if (embeddedVse())
			vseName = "vse-" + Utility.getUserName();
		else
			vseName= StringUtils.defaultIfEmpty(deployServiceToVse, CONFIG.deployServiceToVse());
		return vseName;
	}

	/**
	 * Devtest poweruser login.
	 * 
	 * @return devtest user
	 */
	public static String login(String login) {

		return StringUtils.defaultIfEmpty(login, CONFIG.login());
	}

	/**
	 * 
	 * @param group
	 * @return groupName of SV. If null return ThreadName
	 */
	public static String group(String group) {

		return StringUtils.defaultIfEmpty(group, Thread.currentThread().getName());
	}

	/**
	 * Devtest poweruser password.
	 * 
	 * @return devtest user
	 */
	public static String password(String password) {

		return StringUtils.defaultIfEmpty(password, CONFIG.password());
	}

	/**
	 * Devtest API protocol .
	 * 
	 * @return devtest user
	 */
	public static String protocol(String protocol) {

		return StringUtils.defaultIfEmpty(protocol, CONFIG.protocol());
	}

	public static String keystore(String keystore){

		return StringUtils.defaultIfEmpty(keystore, CONFIG.keystore());
	}

	public static String keystorePassword(String keystorePassword){
		return StringUtils.defaultIfEmpty(keystorePassword, CONFIG.keystorePassword());
	}
	/**
	 * Registry URL
	 * 
	 * @return url to access to registry
	 */
	public static String registryUrl() {

		return CONFIG.registryUrl();
	}
	/**
	 * Registry PORT
	 * 
	 * @return url to access to registry
	 */
	public static String registryPort() {

		return CONFIG.registryPort();
	}
	
	public static boolean undeployIfExist() {
		return CONFIG.undeployIfExist() != null && CONFIG.undeployIfExist().equals("true") ? true : false;
	}
	/**
	 * DevTest home directory.
	 * 
	 * @return DevTest Home driectory server name.
	 */
	public static File devTestHome() {

		return new File(CONFIG.devTestHome());
	}

	/**
	 * embeddedVse
	 * 
	 * @return true if VSE is embeddedVse
	 */

	public static boolean embeddedVse() {

		return CONFIG.embeddedVse();
	}

}

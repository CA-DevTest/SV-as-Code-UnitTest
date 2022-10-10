package com.ca.devtest.sv.devtools;
import com.ca.devtest.sv.devtools.utils.CryptUtil;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@Config.HotReload(1)
@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:local-svascode.properties","classpath:svascode.properties"})
@Config.DecryptorClass( CryptUtil.class )
public interface SVasCodeConfig extends Config {

	
	/**
	 * DevTest home directory.
	 * 
	 * @return registry server name.
	 */
	@Key("devtest.home")
	String devTestHome();
	/**
	 * Registry server name. By default 'localhost'.
	 * 
	 * @return registry server name.
	 */
	@Key("devtest.registry")
	String registryHost();
	/**
	 * Registry server port. By default '1505'.
	 * 
	 * @return registry server name.
	 */
	@Key("devtest.registry.port")
	String registryPort();
	/**
	 * VSE name. By default 'VSE'.
	 * 
	 * @return VSE name
	 */
	@Key("devtest.vsename")
	String deployServiceToVse();

	/**
	 * Devtest user. By default 'svpower'
	 * 
	 * @return devtest user
	 */
	@Key("devtest.login")
	String login();

	/**
	 * Devtest password. By default 'svpower'
	 * 
	 * @return devtest password
	 */
	@Key("devtest.password")
	@EncryptedValue
	String password();
	
	/**
	 * Protocol to access API. By default 'http'
	 * 
	 * @return protocol to access api
	 */
	@Key("devtest.protocol")
	String protocol() ;
	/**
	 * Registry URL
	 * 
	 * @return url to access to registry
	 */
	@Key("devtest.registryUrl")
	String registryUrl();
	
	/**
	 * embeddedVse
	 * 
	 * @return true if VSE is embeddedVse
	 */
	@Key("devtest.embeddedVse")
	boolean embeddedVse();

	/**
	 * Keystore
	 *
	 * @return return keystore
	 */
	@Key("devtest.keystore")
	String keystore();

	/**
	 * Keystore password
	 *
	 * @return return keystore password
	 */
	@Key("devtest.keystorePassword")
	@EncryptedValue
	String keystorePassword();

	/**
	 * Undeploy existing Virtual Service
	 * @return true if existing Virtual Service should be undeplopyed
	 */
	@Key("devtest.undeploy.ifexists")
	String undeployIfExist();
}

/**
 * 
 */
package com.ca.devtest.sv.devtools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinitionImpl;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceRRPairsBuilder;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceVSMVSIBuilder;
import com.ca.devtest.sv.devtools.services.builder.v3.VirtualServiceV3Builder;

/**
 * @author gaspa03
 *
 */
public class DevTestClient {

	private VirtualServiceEnvironment vse;
	private Map<String, VirtualService> virtualServices;
	private String serviceName;
	private File rrPairsFolder;
	private TransportProtocolDefinitionImpl transportProtocol;
	
	private final VirtualServiceBuilder virtualServiceBuilder= null;
	
	
	// private File zipFile

	/**
	 * @param userName
	 * @param password
	 * @param group
	 */
	public DevTestClient(String proptocol, String registryHostName, String vseNname, String userName, String password,
						 String group, String keystore, String keystorePassword) {
		this.vse = new VirtualServiceEnvironment( proptocol ,registryHostName, vseNname,userName,password,
				group, keystore, keystorePassword);
		this.virtualServices = new HashMap<String, VirtualService>();
	}

	
	/**
	 * @param serviceName
	 * @param rrPairsFolder
	 * @return
	 * @throws IOException
	 */
	public VirtualServiceBuilder fromRRPairs(String serviceName, File rrPairsFolder) throws IOException {
		
		return new VirtualServiceRRPairsBuilder(serviceName, getVse(), rrPairsFolder);
	}
	
	/**
	 * @param serviceName
	 * @return
	 * @throws IOException
	 */
	public VirtualServiceBuilder fromVSMVSI(String serviceName, File workingFolder) throws IOException {
		
		return new VirtualServiceVSMVSIBuilder(serviceName, getVse(), workingFolder);
	}

	public VirtualServiceV3Builder withV3API(String serviceName, File workingDir){
		return new VirtualServiceV3Builder(serviceName, getVse(), workingDir);
	}

	protected VirtualServiceEnvironment getVse() {
		return vse;
	}

	protected void setVse(VirtualServiceEnvironment vse) {
		this.vse = vse;
	}

	protected Map<String, VirtualService> getVirtualServices() {
		return virtualServices;
	}

	protected void setVirtualServices(Map<String, VirtualService> virtualServices) {
		this.virtualServices = virtualServices;
	}
}

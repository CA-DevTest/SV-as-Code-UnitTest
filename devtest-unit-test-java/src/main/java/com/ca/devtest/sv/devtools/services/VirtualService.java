/**
 * 
 */
package com.ca.devtest.sv.devtools.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;
import com.ca.devtest.sv.devtools.annotation.VirtualServiceType;
import com.ca.devtest.sv.devtools.utils.VelocityRender;

/**
 * @author gaspa03
 *
 */
public final class VirtualService extends AbstractVirtualService{
	private File packedVirtualService=null;
	private ExecutionMode executionMode=new ExecutionMode();

	public VirtualService( String name, VirtualServiceEnvironment vse) {
		super(name,VirtualServiceType.RRPAIRS.getType(),VirtualServiceType.RRPAIRS.geturlPattern(), vse);
		this.type=VirtualServiceType.RRPAIRS.getType();
	}
	
	public VirtualService( String name, String type, String url,VirtualServiceEnvironment vse) {
		super(name, type, url, vse);
		this.name = name;
		this.vse = vse;
	}

	/**
	 * @param packedVirtualService the packedVirtualService to set
	 */
	public void setPackedVirtualService(File packedVirtualService) {
		this.packedVirtualService = packedVirtualService;
	}

	/**
	 * @return
	 */
	public File getPackedVirtualService() {
		return packedVirtualService;
	}

	/**
	 * @return the mode
	 */
	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setExecutionMode(ExecutionMode mode) {
		this.executionMode = mode;
	}

	/**
	 * @return Payload to change Service Execution mode
	 * @throws IOException
	 */
	public String buildExcusionModePayload() throws IOException{
		InputStream inputStreamContent =getClass().getClassLoader().getResourceAsStream("virtualize-put-message.xml");
		Map<String, VirtualService> config = new HashMap<String,VirtualService>();
		config.put("virtualService", this);
		return VelocityRender.render(IOUtils.toString(inputStreamContent, Charset.defaultCharset()), config);
	}

	/**
	 * @throws IOException
	 */
	public void deploy() throws IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException {
		getVse().deployService(this);
		getVse().changeExecutionMode(this);

	}
	/**
	 * @throws IOException
	 */
	public void unDeploy() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException,
			KeyManagementException {
		getVse().unDeployService(this);
	}

	public void clean(){
		if(packedVirtualService!=null && packedVirtualService.exists()){
			packedVirtualService.deleteOnExit();
		}
	}
}

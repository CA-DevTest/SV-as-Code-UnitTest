/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.utils.VelocityRender;

/**
 * @author gaspa03
 *
 */
public class TransportProtocolResource implements TransportProtocolDefinition {
	
	private File resource;
	private   Map<String, String> parameters = new HashMap<String,String>();
	private static final Logger LOGGING= LoggerFactory.getLogger(TransportProtocolResource.class);

	/**
	 * @param resource
	 * @param parameters
	 */
	public TransportProtocolResource( File resource) {
		super();
		this.resource=resource;
		
	}
	/**
	 * @return
	 */
	public String toVrsContent() {
		String content=null;
		try {
			content = FileUtils.readFileToString(resource,"UTF-8");
			content=VelocityRender.render(content, parameters) ;
		} catch (IOException e) {
			LOGGING.error("Not able to open resources "+ resource.getPath(), e); 
		
		}
		return content;
	}
	
	@Override
	public Map<String, String> getParameters() {
		
		return parameters;
	}
	
	@Override
	public List<DataProtocolDefinition> getRequestSide() {
		throw new UnsupportedOperationException("method not suported for this implementation");
	}
	@Override
	public List<DataProtocolDefinition> getResponseSide() {
	
		throw new UnsupportedOperationException("method not suported for this implementation");
	}
}

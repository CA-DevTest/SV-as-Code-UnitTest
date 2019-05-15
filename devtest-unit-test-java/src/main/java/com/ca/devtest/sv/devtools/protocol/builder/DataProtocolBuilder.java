/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol.builder;

import java.util.HashMap;
import java.util.Map;

import com.ca.devtest.sv.devtools.protocol.DataProtocolDefinition;

/**
 * @author gaspa03
 *
 */
public class DataProtocolBuilder  implements ParamatrizedBuilder{

	protected final String typebuilder ;
	protected Map<String, String> parameters= new HashMap<String, String>();
	
	public DataProtocolBuilder( String type) {
		super();
		this.typebuilder=type;
	}

	
	 public  void  addKeyValue(String key, String value) {
		parameters.put(key, value);
		
	}

	
	public  DataProtocolBuilder addParameter(String key, String value) {
		addKeyValue(key, value);

	return this;
}


	/**
	 * @return
	 */
	public DataProtocolDefinition  build(){
		
		DataProtocolDefinition  baseProtocol = new  DataProtocolDefinition(typebuilder);
		baseProtocol.getParameters().putAll(parameters);
		return baseProtocol;
	}

	


	
	
}

/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gaspa03
 *
 */
public abstract class BaseProtocol {
	
	private final String type;
	private final HashMap<String, Object> parameters = new HashMap<String, Object>() ;
	
	public BaseProtocol(String type) {
		super();
		this.type=type;
	}

	/**
	 * @return the parameters
	 */
	public final Map<String, Object> getParameters() {
		return parameters;
	}
	/**
	 * @return
	 */
	protected final String printParameters() {
		StringBuilder result= new StringBuilder();
		
		if( !parameters.isEmpty()){
			Set<String> keys=parameters.keySet();
			for (String key : keys) {
				result.append("<").append(key).append(">").append(parameters.get(key)).append("</").append(key).append(">");
			}
		}
		return result.toString();
	}
	/**
	 * @param definition
	 */
	protected void printXml(StringBuilder definition){
		definition.append("<Protocol type=\"").append(type).append("\">").append(printParameters()).append(doPrintSpecific()).append("</Protocol>");
		
	}

	protected abstract String doPrintSpecific();
}


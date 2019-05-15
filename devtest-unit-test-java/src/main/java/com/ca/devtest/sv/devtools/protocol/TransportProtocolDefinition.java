/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol;

import java.util.List;
import java.util.Map;

/**
 * @author gaspa03
 *
 */
public interface TransportProtocolDefinition {
	

	/**
	 * @return
	 */
	public Map<String, String> getParameters();
	/**
	 * @return
	 */
	public String toVrsContent();
	
	/**
	 * @return
	 */
	public List<DataProtocolDefinition> getRequestSide();
	
	/**
	 * @return
	 */
	public List<DataProtocolDefinition> getResponseSide();


}

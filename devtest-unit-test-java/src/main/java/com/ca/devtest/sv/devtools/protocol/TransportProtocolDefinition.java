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
    Map<String, String> getParameters();
	/**
	 * @return
	 */
    String toVrsContent();
	
	/**
	 * @return
	 */
    List<DataProtocolDefinition> getRequestSide();
	
	/**
	 * @return
	 */
    List<DataProtocolDefinition> getResponseSide();


}

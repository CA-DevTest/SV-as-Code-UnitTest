/**
 * 
 */
package com.ca.devtest.sv.devtools.vse;

import com.ca.devtest.sv.devtools.utils.SvAsCodeConfigUtil;

/**
 * @author gaspa03
 *
 */
public final class VirtualServerEnvironmentFactory {
	
	
	
	public static VirtualServerEnvironment getVseInstance(String aRegistry, String aVseName){
		VirtualServerEnvironment instance=null;
		if(SvAsCodeConfigUtil.embeddedVse()){
			instance=   VirtualServerEnvironmentLocal.getInstance(aRegistry,aVseName);
		}else{
			instance= new VirtualServerEnvironmentRemote(aRegistry,aVseName);
		}
		return instance;
	}

}

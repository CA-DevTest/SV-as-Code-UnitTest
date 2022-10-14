/**
 * 
 */
package com.ca.devtest.sv.devtools.services.builder;

import java.io.File;
import java.io.IOException;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;
import com.ca.devtest.sv.devtools.utils.PackVirtualService;

/**
 * @author gaspa03
 *
 */
public final class VirtualServiceRRPairsBuilder extends VirtualServiceBuilder {
	private File rrPairsFolder;
	
	
	/**
	 * @return the rrPairsFolder
	 */
	private File getRrPairsFolder() {
		return rrPairsFolder;
	}

	/**
	 * @param rrPairsFolder the rrPairsFolder to set
	 */
	private void setRrPairsFolder(File rrPairsFolder) {
		this.rrPairsFolder = rrPairsFolder;
	}

	public VirtualServiceRRPairsBuilder(String name, VirtualServiceEnvironment vse, File rrpairsFolder){
		super(name,vse);
		this.rrPairsFolder=rrpairsFolder;
	}
	
	
	
	protected File packVirtualService() throws IOException {
	
		return PackVirtualService.packVirtualService(getRrPairsFolder(), getParameters(),generateVrsContent(),generateServicePropertiesContent());
	}
}

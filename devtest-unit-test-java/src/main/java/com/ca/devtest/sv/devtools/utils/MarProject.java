/**
 * 
 */
package com.ca.devtest.sv.devtools.utils;

import java.io.File;

/**
 * @author gaspa03
 *
 */
public final  class MarProject {
	private final File projectConfig;
	private final File vsm;
	private final File vsi;
	private File[] otherFiles;
	
	/**
	 * @param projectConfig
	 * @param vsm
	 * @param vsi
	 */
	public MarProject(File projectConfig, File vsm,File vsi) {
		
		super();
		this.projectConfig=projectConfig;
		this.vsm=vsm;
		this.vsi=vsi;
	}

	/**
	 * @return the otherFiles
	 */
	public File[] getOtherFiles() {
		return otherFiles;
	}

	/**
	 * @param otherFiles the otherFiles to set
	 */
	public void setOtherFiles(File[] otherFiles) {
		this.otherFiles = otherFiles;
	}

	/**
	 * @return the vsi
	 */
	public File getVsi() {
		return vsi;
	}

	/**
	 * @return the projectConfig
	 */
	public File getProjectConfig() {
		return projectConfig;
	}
	
	

}

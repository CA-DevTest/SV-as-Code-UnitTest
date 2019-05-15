/**
 * 
 */
package com.ca.devtest.sv.devtools.vse;

import java.io.IOException;

/**
 * @author gaspa03
 *
 */
public class VirtualServerEnvironmentRemote implements VirtualServerEnvironment {

	public VirtualServerEnvironmentRemote(String aRegistry, String aVseName) {
		
	}

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.vse.VirtualServerEnvironment#start()
	 */
	@Override
	public boolean start() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.vse.VirtualServerEnvironment#stop()
	 */
	@Override
	public boolean stop() throws RuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.vse.VirtualServerEnvironment#isRunning()
	 */
	@Override
	public boolean isRunning() throws RuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

}

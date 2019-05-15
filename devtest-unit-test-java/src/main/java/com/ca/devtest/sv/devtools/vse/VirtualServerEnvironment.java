package com.ca.devtest.sv.devtools.vse;

import java.io.IOException;

public interface VirtualServerEnvironment {

	/**
	 * first time install VSE from Jar and start VSE in fork JVM
	 * @return true if VSE is started
	 * @throws IOException
	 */
	boolean start() throws IOException;

	/**
	 * stop VSE process
	 * 
	 * @return true if VSE is started
	 * @throws RuntimeException
	 *             if start method is not called before
	 */
	boolean stop() throws RuntimeException;
	
	/**
	 
	 * 
	 * @return true if VSE is running
	 * @throws RuntimeException
	 *             if start method is not called before
	 */
	boolean isRunning() throws RuntimeException;

}
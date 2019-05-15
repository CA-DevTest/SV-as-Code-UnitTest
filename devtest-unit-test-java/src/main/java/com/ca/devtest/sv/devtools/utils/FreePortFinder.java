package com.ca.devtest.sv.devtools.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author gaspa03
 *
 */
public final class FreePortFinder {

	
	/**
	 * @param from
	 * @param to
	 * @return available port in range 
	 */
	public static int nextFreePort(int from, int to) {
	    int port = randomPort(from, to);
	    while (true) {
	        if (isLocalPortFree(port)) {
	            return port;
	        } else {
	            port = randomPort(from, to);
	        }
	    }
	}

	private static int randomPort(int from, int to) {
		
		return ThreadLocalRandom.current().nextInt(from, to);
	}

	/**
	 * @param port
	 * @return true is port is available
	 */
	private static boolean isLocalPortFree(int port) {
	    try {
	        new ServerSocket(port).close();
	        return true;
	    } catch (IOException e) {
	        return false;
	    }
	}
}

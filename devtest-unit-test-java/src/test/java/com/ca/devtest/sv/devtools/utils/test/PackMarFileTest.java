package com.ca.devtest.sv.devtools.utils.test;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ca.devtest.sv.devtools.utils.PackMarFile;

public class PackMarFileTest {

	@Test
	public void test() throws Exception {
		
 
		File workingFolder = new File(getClass().getClassLoader().getResource("mar/vsm/proto").toURI());
		Map<String, String> config= new HashMap<String, String>();
		
		SimpleDateFormat df= new SimpleDateFormat("YYYY-MM-dd.HH:mm:ss.SSS.Z");
		
		config.put("dateOfMar", df.format(new Date()));
		config.put("hostname", getHostName());
		
		File zip = PackMarFile.packVirtualService(workingFolder,"demo", config);
		zip.delete();
	}

	private String getHostName() {
		String result="UNKNOWN";
		 try {
	            InetAddress address = InetAddress.getLocalHost();

	            result= address.getHostName();
	        } catch (UnknownHostException e) {
	           
	        }
		return result;
	}

}

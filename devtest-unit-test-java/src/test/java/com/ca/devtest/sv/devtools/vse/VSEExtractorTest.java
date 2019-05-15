package com.ca.devtest.sv.devtools.vse;

import java.net.JarURLConnection;
import java.net.URL;

import org.junit.Test;

import com.ca.devtest.sv.devtools.utils.SvAsCodeConfigUtil;

public class VSEExtractorTest {

	
/*
	@Test
	public void startVse() throws Exception {
		VirtualServerEnvironment vseWrapper= new VirtualServerEnvironmentLocal();
		vseWrapper.start();
		Thread.sleep(10000);
		vseWrapper.stop();
	}
*/	
	@Test
	public void vseJarLocation() {
		
		URL url=Thread.currentThread().getContextClassLoader().getResource("lisa.properties");
		try {

			JarURLConnection conn = (JarURLConnection) url.openConnection();
			URL urlJar = conn.getJarFileURL();
		
			System.out.println(	SvAsCodeConfigUtil.devTestHome());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}


package com.ca.devtest.sv.devtools;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.ca.devtest.sv.devtools.annotation.Config;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.VirtualServiceType;
import com.ca.devtest.sv.devtools.junit.VirtualServiceClassScopeRule;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

@DevTestVirtualServer(deployServiceToVse = "VSE",groupName="Test")
@DevTestVirtualService(serviceName = "Proxy", type = VirtualServiceType.VSM, workingFolder = "mar/vsm/proto" )
public class GlobalVirtualServiceTest {

	// handle VS with Class scope
	@ClassRule
	public static VirtualServiceClassScopeRule clazzRule = new VirtualServiceClassScopeRule();
	//
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	
	
	
	@DevTestVirtualServiceFromVrs(serviceName = "demo", workingFolder = "rrpairs/soapWithVrs", parameters={ @Parameter(name = "ClientID", value = "9002"), }, 
			vrsConfig = @Config(value = "transport.vrs",
			parameters = { @Parameter(name = "port", value = "9002"), @Parameter(name = "basePath", value = "/lisa") }))
	

	@Test
	public void test1() {
	
	}

	@DevTestVirtualServiceFromVrs(serviceName = "demo", workingFolder = "rrpairs/soapWithVrs", vrsConfig = @Config(value = "transport.vrs", parameters = {
			@Parameter(name = "port", value = "9002"), @Parameter(name = "basePath", value = "/lisa") }))
	@Test
	public void test2() {
	
	}
}

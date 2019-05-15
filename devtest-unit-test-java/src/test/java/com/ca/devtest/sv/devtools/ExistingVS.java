package com.ca.devtest.sv.devtools;

import org.junit.Rule;
import org.junit.Test;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.VirtualServiceType;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

@DevTestVirtualServer(groupName="Test")
public class ExistingVS {
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	

	@DevTestVirtualService(serviceName = "Proxy", type = VirtualServiceType.VSM, workingFolder = "mar/vsm/proto" )
	@Test
	public void deployExistingService() {
		System.out.println("demo");
	}

}

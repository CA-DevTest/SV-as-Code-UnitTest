
package com.ca.devtest.lisabank.demo.sv.vsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.BankService;
import com.ca.devtest.lisabank.wsdl.User;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.annotation.VirtualServiceType;
import com.ca.devtest.sv.devtools.junit.VirtualServiceClassScopeRule;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)
@DevTestVirtualServer()
@DevTestVirtualService(serviceName = "Proxy", type = VirtualServiceType.VSM, 
			workingFolder = "vsm/lisabank", parameters = {
		@Parameter(name = "port", value = "9081") })
public class ExistingVirtualServiceTest {

	// handle VS with Class scope
	@ClassRule
	public static VirtualServiceClassScopeRule clazzRule = new VirtualServiceClassScopeRule();
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();

	static final Log logger = LogFactory.getLog(ExistingVirtualServiceTest.class);
	@Autowired
	private BankService bankServices;


	@Test
	public void getListUser() {

		try {
			User[] users = bankServices.getListUser();
			assertNotNull(users);
			printUsers(users);
			assertEquals(9, users.length);
		} finally {
			
		}

	}

	private void printUsers(User[] users) {
		for (User user : users) {
			logger.info(user.getFname() + " " + user.getLname() + " " + user.getLogin());
		}

	}
	
}

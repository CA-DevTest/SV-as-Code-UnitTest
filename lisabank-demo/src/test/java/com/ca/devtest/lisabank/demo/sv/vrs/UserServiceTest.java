package com.ca.devtest.lisabank.demo.sv.vrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.BankService;
import com.ca.devtest.lisabank.wsdl.User;
import com.ca.devtest.sv.devtools.annotation.Config;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)
@DevTestVirtualServer()
public class UserServiceTest {
	static final Log logger = LogFactory.getLog(UserServiceTest.class);

	@Autowired
	private BankService bankServices;

	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	
	@DevTestVirtualServiceFromVrs(serviceName = "getUserWithVrs", workingFolder = "soapWithVrs", vrsConfig = @Config(value = "transport.vrs", parameters = {
			@Parameter(name = "port", value = "9081"),
			@Parameter(name = "basePath", value = "/itkoExamples/EJB3UserControlBean") }))
	@Test
	public void getUser() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		printUsers(users);
		assertEquals(1, users.length);
	}

	private void printUsers(User[] users) {
		for (User user : users) {
			logger.info(user.getFname() + " " + user.getLname() + " " + user.getLogin());
		}

	}
}

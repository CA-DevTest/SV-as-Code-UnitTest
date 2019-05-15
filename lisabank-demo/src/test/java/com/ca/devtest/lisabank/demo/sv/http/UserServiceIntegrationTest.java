package com.ca.devtest.lisabank.demo.sv.http;

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
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.junit.VirtualServiceClassScopeRule;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)

@DevTestVirtualServer()
public class UserServiceIntegrationTest {
	static final Log logger = LogFactory.getLog(UserServiceIntegrationTest.class);
	@Autowired
	private BankService bankServices;
	@ClassRule
	public static VirtualServiceClassScopeRule ruleClass= new VirtualServiceClassScopeRule();
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();

	@DevTestVirtualService(serviceName = "UserServiceTest-EJB3UserControlBean", 
			port = 9081, basePath = "/itkoExamples/EJB3UserControlBean",
			workingFolder = "UserServiceTest/getListUser/EJB3UserControlBean",
			requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
	
	@Test
	public void getListUser() {
		// Given

		// When
		User[] users = bankServices.getListUser();
		// Then
		printUsers(users);
		assertNotNull(users);
		assertEquals(9, users.length);
		User user = getUser("Admin", users);
		assertEquals("Admin", user.getLname());

	}



	/**
	 * @param users
	 */
	private void printUsers(User[] users) {
		for (User user : users) {
			logger.info(user.getFname() + " " + user.getLname() + " " + user.getLogin());
		}

	}

	/**
	 * @param name
	 * @param users
	 * @return
	 */
	private User getUser(String name, User[] users) {

		User result = null;
		for (User user : users) {
			if (name.equals(user.getLname())) {
				result = user;
			}

		}
		return result;
	}
}

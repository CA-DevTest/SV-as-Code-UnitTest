package com.ca.devtest.lisabank.demo.sv.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ca.devtest.sv.devtools.annotation.*;
import com.ca.devtest.sv.devtools.annotation.v3.*;
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
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

/**
 * @author gaspa03
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)
@DevTestVirtualServer()
public class BasicTest {
	static final Log logger = LogFactory.getLog(BasicTest.class);
	@Autowired
	private BankService bankServices;

	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	
	@DevTestVirtualService(serviceName = "getListUser0",
				basePath = "/itkoExamples/EJB3UserControlBean",
			port = 9081,
			workingFolder = "UserServiceTest/getListUser/EJB3UserControlBean",
			requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	
	@Test
	public void getListUser() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		printUsers(users);
		assertEquals(9, users.length);
	}

	@DevTestVirtualServiceV3(serviceName = "getListUserV3",
			basePath = "/itkoExamples/EJB3UserControlBean",
			port = "9081",
			workingFolder = "UserServiceTest/getListUser/EJB3UserControlBeanV3",
			inputFile1 = "listUsers-req.xml",
			inputFile2 = "listUsers-rsp.xml",
			dataProtocolsConfig = {
				@DataProtocolConfig(
					typeId = "SOAPDPH"
				)
			}
	)
	@Test
	public void getListUserV3() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		printUsers(users);
		assertEquals(9, users.length);
	}

	@DevTestVirtualService(serviceName = "getListUser1",
			basePath = "/itkoExamples/EJB3UserControlBean", 
			port = 9081, 
			workingFolder = "UserServiceTest/getListUser/EJB3UserControlBean1", 
			requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUser1() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		printUsers(users);
		assertEquals(0, users.length);
	}

	@DevTestVirtualServiceV3(serviceName = "getListUser1V3",
			basePath = "/itkoExamples/EJB3UserControlBean",
			port = "9081",
			workingFolder = "UserServiceTest/getListUser/EJB3UserControlBean1V3",
			inputFile1 = "listUsers-req.xml",
			inputFile2 = "listUsers-rsp.xml",
			dataProtocolsConfig = {
				@DataProtocolConfig(
					typeId = "SOAPDPH"
				)
			}
	)
	@Test
	public void getListUser1V3() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		printUsers(users);
		assertEquals(0, users.length);
	}

	@DevTestVirtualService(serviceName = "getListUserTemplate", 
			basePath = "/itkoExamples/EJB3UserControlBean", 
			port = 9081, workingFolder = "UserServiceTest/getListUser/template",
			parameters={@Parameter(name="email", value="pascal.gasp@gmail.com"),
			@Parameter(name="nom", value="Gasp"),
			@Parameter(name="login", value="pgasp"),
			@Parameter(name="pwd", value="HELLO")},
			requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUserTemplate() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		assertEquals(1, users.length);
		printUsers(users);
	}

	@DevTestVirtualServiceV3(serviceName = "getListUserTemplateV3",
			basePath = "/itkoExamples/EJB3UserControlBean",
			port = "9081",
			workingFolder = "UserServiceTest/getListUser/template",
			inputFile2 = "listUser-1-req.xml",
			inputFile1 = "listUser-1-rsp.xml",
			parameters={@Parameter(name="email", value="pascal.gasp@gmail.com"),
					@Parameter(name="nom", value="Gasp"),
					@Parameter(name="login", value="pgasp"),
					@Parameter(name="pwd", value="HELLO")},
			dataProtocolsConfig = {
					@DataProtocolConfig(
							typeId = "SOAPDPH"
					)
			}
	)
	@Test
	public void getListUserTemplateV3() {
		User[] users = bankServices.getListUser();
		assertNotNull(users);
		assertEquals(1, users.length);
		printUsers(users);
	}
	private void printUsers(User[] users) {
		for (User user : users) {
			logger.info(user.getFname() + " " + user.getLname() + " " + user.getLogin());
		}
	}
}

package com.ca.devtest.lisabank.demo.sv.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.LisaUserService;
import com.ca.devtest.lisabank.demo.model.LisaUser;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)
@DevTestVirtualServer()
public class LisaUserServiceTest {
	static final Log logger = LogFactory.getLog(LisaUserServiceTest.class);
	@Autowired
	private LisaUserService lisaUserService;

	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	
	@DevTestVirtualService(serviceName = "getLisaUser",
			basePath = "/", 
			port = 9904, 
			workingFolder = "rrpairs/rest", 
			requestDataProtocol = {	
			@Protocol(ProtocolType.DPH_REST) })
	@Test
	public void getLisaUser() {
		List<LisaUser> users = lisaUserService.getListUsers();
		assertNotNull(users);
		printUsers(users);
		assertEquals(1, users.size());
	}

	private void printUsers(List<LisaUser> users) {
		for (LisaUser user : users) {
			logger.info(user.getFname() + " " + user.getLname() + " " + user.getLogin());
		}

	}

	@DevTestVirtualService(serviceName = "getUserByJSON",
			basePath = "/",
			port = 9904,
			workingFolder = "rrpairs/rest",
			requestDataProtocol = {
					@Protocol(ProtocolType.DPH_JSON) })
	@Test
	public void getUserByJSON() {

		List<LisaUser> users = lisaUserService.getListUsers();
		assertNotNull(users);
		printUsers(users);
		assertEquals(1, users.size());
	}
}

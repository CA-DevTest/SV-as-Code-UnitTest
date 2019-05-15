/**
 * 
 */
package com.ca.devtest.lisabank.demo.sv.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.BankService;
import com.ca.devtest.lisabank.wsdl.Account;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.junit.VirtualServiceClassScopeRule;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

/**
 * @author gaspa03
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)
@DevTestVirtualServer()

public class AccountServiceTest {
	@Autowired
	private BankService bankServices;
	
	// Rule to handle Class scope Devtest Annotations
	@ClassRule
	public static VirtualServiceClassScopeRule ruleClass= new VirtualServiceClassScopeRule();
	// Rule to handle Method scope Devtest Annotations
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();

	@DevTestVirtualService(serviceName = "UserServiceTest-EJB3UserControlBean", 
	port = 9081, basePath = "/itkoExamples/EJB3UserControlBean", 
	workingFolder = "AccountServiceTest/createUserWithCheckingAccount/EJB3UserControlBean", 
	requestDataProtocol = {
	@Protocol(ProtocolType.DPH_SOAP) })
	
	@DevTestVirtualService(serviceName = "UserServiceTest-EJB3AccountControlBean", 
	port = 9081, basePath = "/itkoExamples/EJB3AccountControlBean", 
	workingFolder = "AccountServiceTest/createUserWithCheckingAccount/EJB3AccountControlBean", 
	requestDataProtocol = {
	@Protocol(ProtocolType.DPH_SOAP) })
	
	
	@Test
	public void createUserWithCheckingAccount() {
	
		// Given
		String user = "pascal";
		String password = "password";
		int amount = 1000;
		// prepare context
		// bankServices.deleteUser(user);
		// When
		Account account = bankServices.createUserWithCheckingAccount(user, password, amount);
		// Then
		assertNotNull(account);
		assertEquals("Le balance du compte n'est pas conforme", amount, account.getBalance().intValue());
	}

	
	
	
}

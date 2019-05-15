
package com.ca.devtest.sv.devtools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.ca.devtest.sv.devtools.annotation.Config;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.application.SoapClient;
import com.ca.devtest.sv.devtools.junit.VirtualServiceClassScopeRule;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

/**
 * @author gaspa03
 *
 */
@DevTestVirtualServer(deployServiceToVse = "vse-perf")
public class DevTesClientRRPairs {
	

	// handle VS with Class scope
	@ClassRule
	public static VirtualServiceClassScopeRule clazzRule = new VirtualServiceClassScopeRule();
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	
	

	/**
	 * @throws IOException
	 * @throws URISyntaxException
	 */

	@DevTestVirtualService(serviceName = "lisa", port = 9001, basePath = "/lisa", workingFolder = "rrpairs/soap", 
			parameters = {
					@Parameter(name = "port", value = "8999"), 
					@Parameter(name = "basePath", value = "/errorManagement") },
			requestDataProtocol = {
			@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void createSoapService() throws IOException, URISyntaxException {
		int port = 9001;
		String path = "/lisa";
		/* Test */
		SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
		URL url = getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml");
		File requestFile = new File(getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml").toURI());
		String request = FileUtils.readFileToString(requestFile, "UTF-8");
		String response = soapclient.callService(path, request);

	}

	@DevTestVirtualServiceFromVrs(serviceName = "demo", workingFolder = "rrpairs/soapWithVrs", vrsConfig = @Config(value = "transport.vrs", parameters = {
			@Parameter(name = "port", value = "9002"), @Parameter(name = "basePath", value = "/lisa") }))
	@Test
	public void createSoapServicFromVrs() throws IOException, URISyntaxException {
		int port = 9002;
		String path = "/lisa";
		/* Test */
		SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
		URL url = getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml");
		File requestFile = new File(getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml").toURI());
		String request = FileUtils.readFileToString(requestFile, "UTF-8");
		String response = soapclient.callService(path, request);

	}

	@DevTestVirtualServiceFromVrs(serviceName = "errorManagement", workingFolder = "rrpairs/errorManagement",

			vrsConfig = @Config(value = "errorManagement.vrs", parameters = { @Parameter(name = "port", value = "8999"),
					@Parameter(name = "basePath", value = "/errorManagement") }))
	@Test
	public void createErrorManagementSVFromVrs() throws IOException, URISyntaxException {
		int port = 8999;
		String path = "/errorManagement";
		/* Test */
		SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
		URL url = getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml");
		File requestFile = new File(getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml").toURI());
		String request = FileUtils.readFileToString(requestFile, "UTF-8");
		String response = soapclient.callService(path, request);

	}

	@DevTestVirtualServiceFromVrs(serviceName = "oms", workingFolder = "rrpairs/searchOrder", vrsConfig = @Config(value = "searchOrder-FinalV2.vrs", parameters = {
			@Parameter(name = "port", value = "7002"), @Parameter(name = "basePath", value = "/") }))
	@Test
	public void createJsonServiceFromVrs() throws IOException, URISyntaxException {
		int port = 7002;
		String path = "/";
		/* Test */
		SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
		File requestFile = new File(
				getClass().getClassLoader().getResource("rrpairs/searchOrder/searchOrder-Final-1-req.xml").toURI());
		String request = FileUtils.readFileToString(requestFile, "UTF-8");
		String response = soapclient.callJSONService(path, request);

	}
	
	
	@DevTestVirtualServiceFromVrs(serviceName = "swagger", workingFolder = "rrpairs/swagger", vrsConfig = @Config(value = "swagger.vrs", parameters = {
			@Parameter(name = "port", value = "8010"), @Parameter(name = "basePath", value = "/") }))
	@Test
	public void createSwaggerServiceFromVrs() throws IOException, URISyntaxException {
		int port = 8010;
		String path = "/";
		/* Test */
		SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
		File requestFile = new File(
				getClass().getClassLoader().getResource("rrpairs/searchOrder/searchOrder-Final-1-req.xml").toURI());
		String request = FileUtils.readFileToString(requestFile, "UTF-8");
		String response = soapclient.callJSONService(path, request);

	}
}

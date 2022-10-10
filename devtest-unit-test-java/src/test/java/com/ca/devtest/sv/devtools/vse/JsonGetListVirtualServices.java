package com.ca.devtest.sv.devtools.vse;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class JsonGetListVirtualServices {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Test
	public void listVirtualService() throws URISyntaxException, IOException {
		URL urlResource = getClass().getClassLoader().getResource("devtest-api/getServices.json");
		String vseName = "vse-perf";
		File jsonMessage = new File(urlResource.toURI());
		String json = FileUtils.readFileToString(jsonMessage, Charset.defaultCharset());
		ReadContext ctx = JsonPath.parse(json);
		String expression = String.format("$.vseList[?(@.name=='%s')]..virtualServiceList[*].name", vseName);
		List<String> services = ctx.read(expression);
		LOG.info(services.toString());
		assertEquals(2, services.size());
	}
	
	@Test
	public void listVirtualServiceFromVSE() throws URISyntaxException, IOException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		VirtualServiceEnvironment vse=new VirtualServiceEnvironment("http", "localhost","VSE","admin","admin","","","");
		List<VirtualServiceInterface> services=vse.listVirtualServices();
		assertEquals(0, services.size());
	}

}

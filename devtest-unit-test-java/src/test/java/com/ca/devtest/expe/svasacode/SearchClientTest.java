
package com.ca.devtest.expe.svasacode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import com.ca.devtest.sv.devtools.annotation.Config;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;

@DevTestVirtualServer(registryHost = "localhost", deployServiceToVse = "VSE", groupName = "AP10534")
public class SearchClientTest {

	private String END_POINT = "http://%s:%s%s";

	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();

	@DevTestVirtualServiceFromVrs(serviceName = "test_search_client", workingFolder = "rrpairs/search_client", vrsConfig = @Config(value = "vrs_template.xml", parameters = {
			@Parameter(name = "listenport", value = "8118"), @Parameter(name = "targetHost", value = "S00VA9932206"),
			@Parameter(name = "targetPort", value = "80"), @Parameter(name = "basePath", value = "/CMIGB") }))
	@Test
	public void test() {
		System.out.println("bbbb");
	}

	public String callJSONService(String server, String port, String path, String request) throws IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(String.format(END_POINT, server, port, path));
		HttpEntity content = new StringEntity(request, ContentType.APPLICATION_JSON);
		post.setEntity(content);
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new HttpResponseException(response.getStatusLine().getStatusCode(), "Service responding with error ");
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
}
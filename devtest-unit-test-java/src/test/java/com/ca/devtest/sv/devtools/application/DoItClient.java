/**
 * 
 */
package com.ca.devtest.sv.devtools.application;

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
import org.junit.Test;

/**
 * @author gaspa03
 *
 */
public class DoItClient {

	private String DOIT_REQUEST= "ressource=instance&fonction=ref_instance&ID=%3As00va9930281%3A%5Bs00va9930281.sa-sm-prez-04.HMHVUW9YdZA%5D++%3A01062016%3A17%3A20%3A29%23089%3A&TYPE_Q=SB&TYPE_R=IB&arg0=LECTURE_INSTANCE&arg1=b0b0b7b4b0b0b5b0b0b0b1b0b0b8b1b5b4b0b0b0b0b1b0b5b2b1b0b1b3c3c5d4c5ccc5cdcfceccc9cec5b0b5b2b8b0b0b1b0b0b7b5b2b0b1b6ccc5c3d4d5d2c5dfc9ced3d4c1cec3c5b3b6b3b7b0b0b1b0";
	private String DOIT_SERVICE= "http://%s:%s/cgi-bin/GatewayJavaDoIt.cgi";
	
	private String server="";
	private String port="";
	
	
	public DoItClient(String server, String port) {
		super(); 
		this.server=server;
		this.port=port;
	}
	@Test
	public String callDoItService() throws  IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(String.format(DOIT_SERVICE, server, port));
		HttpEntity content= new StringEntity(DOIT_REQUEST,ContentType.APPLICATION_FORM_URLENCODED);
		post.setEntity(content);
		HttpResponse response = client.execute(post);

		
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new HttpResponseException(response.getStatusLine().getStatusCode(),
					"Service responding with error ");
		}
		
		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
}

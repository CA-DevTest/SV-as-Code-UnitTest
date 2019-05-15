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

/**
 * @author gaspa03
 *
 */
public class SoapClient {
	private String server="";
	private String port="";
	private String END_POINT= "http://%s:%s%s";
	
	public SoapClient(String server, String port) {
		super(); 
		this.server=server;
		this.port=port;
	}
	public String callService(String path,String request) throws  IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(String.format(END_POINT, server, port,path));
		HttpEntity content= new StringEntity(request,ContentType.APPLICATION_XML);
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
	
	public String callJSONService(String path,String request) throws  IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(String.format(END_POINT, server, port,path));
		HttpEntity content= new StringEntity(request,ContentType.APPLICATION_JSON);
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

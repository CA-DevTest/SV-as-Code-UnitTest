/**
 * 
 */
package com.ca.devtest.sv.devtools.utils;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gaspa03
 *
 */
public class RegistryRestAPI {

	protected static final String CREATE_VS_URI = "http://%s:1505/api/Dcm/VSEs/%s/actions/createService";
	protected static final String GET_VSE_URI = "http://%s:1505/api/Dcm/VSEs/%s/";
	private static final Logger LOG = LoggerFactory.getLogger(RegistryRestAPI.class);

	/**
	 * @param registryHostName
	 * @param vseName
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static boolean isVseStarted(String registryHostName, String vseName) throws ParseException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		String urlGet = String.format(GET_VSE_URI, registryHostName, vseName);

		HttpGet get = new HttpGet(urlGet);

		get.setHeader("Authorization", String.format("Basic %s", new String(Base64.encodeBase64(
				new String(SvAsCodeConfigUtil.login(null) + ":" + SvAsCodeConfigUtil.password(null)).getBytes()))));

		HttpResponse response = httpClient.execute(get);

		LOG.info("Get VSE Status  " + vseName + "....");
		HttpEntity entity = response.getEntity();

		LOG.info("Server Response Code :" + response.getStatusLine().getStatusCode());
		if (LOG.isDebugEnabled()) {
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("Server respond :" + responseString);
		}

		return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
	}

	/**
	 * @param registryHostName
	 * @param vseName
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static boolean stopVSE(String registryHostName, String vseName) throws ParseException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		String urlGet = String.format(GET_VSE_URI, registryHostName, vseName);

		HttpDelete delete = new HttpDelete(urlGet);

		delete.setHeader("Authorization", String.format("Basic %s", new String(Base64.encodeBase64(
				new String(SvAsCodeConfigUtil.login(null) + ":" + SvAsCodeConfigUtil.password(null)).getBytes()))));

		HttpResponse response = httpClient.execute(delete);

		LOG.info("Delete VSE " + vseName + "....");
		HttpEntity entity = response.getEntity();

		LOG.info("Server Response Code :" + response.getStatusLine().getStatusCode());
		if (LOG.isDebugEnabled()) {
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("Server respond :" + responseString);
		}

		return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
	}
}

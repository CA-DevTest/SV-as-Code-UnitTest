package com.ca.devtest.sv.devtools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.utils.SvAsCodeConfigUtil;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class VirtualServiceEnvironment {

	private String name;
	private String registryHostName;
	private final String group;
	private final String userName;
	private final String password;
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	protected static final String CREATE_VS_URI = "http://%s:%s/api/Dcm/VSEs/%s/actions/createService";
	protected static final String GET_DELETE_PUT_VS_URI = "http://%s:%s/api/Dcm/VSEs/%s/%s";
	protected static final String GET_SERVICES_URI = "http://%s:%s/api/Dcm/VSEs";
	protected static final String GET_SERVICE_PATH = "$.vseList[?(@.name=='%s')]..virtualServiceList[*].name";

	public VirtualServiceEnvironment(String registryHostName, String name, String userName, String password,
			String group) {
		super();
		this.name = SvAsCodeConfigUtil.deployServiceToVse(name);
		this.group = SvAsCodeConfigUtil.group(group);
		this.registryHostName = SvAsCodeConfigUtil.registryHost(registryHostName);
		this.userName = SvAsCodeConfigUtil.login(userName);
		this.password = SvAsCodeConfigUtil.password(password);

	}
	public VirtualServiceEnvironment() {
		super();
		this.name = SvAsCodeConfigUtil.deployServiceToVse(null);
		this.group = SvAsCodeConfigUtil.group(null);
		this.registryHostName = SvAsCodeConfigUtil.registryHost(null);
		this.userName = SvAsCodeConfigUtil.login(null);
		this.password = SvAsCodeConfigUtil.password(null);

	}
	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	protected String getRegistryHostName() {
		return registryHostName;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param service
	 * @throws IOException
	 */
	public void deployService(VirtualService service) throws IOException {

		HttpClient httpClient = HttpClients.createDefault();
		String urlPost = String.format(service.getType().geturlPattern(), getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName());

		HttpPost post = new HttpPost(urlPost);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		FileBody contentBody = new FileBody(service.getPackedVirtualService(), ContentType.APPLICATION_JSON);
		builder.addPart("file", contentBody);
		post.setEntity(builder.build());

		post.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64(new String(userName + ":" + password).getBytes()))));

		HttpResponse response = httpClient.execute(post);

		LOG.info("Deploying service " + service.getDeployedName() + "....");
		HttpEntity entity = response.getEntity();

		LOG.info("Server Response Code :" + response.getStatusLine().getStatusCode());
		if (LOG.isDebugEnabled()) {
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("Server respond :" + responseString);
		}
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {

			LOG.error("Error deploying service :" + service.getDeployedName());
			throw new HttpResponseException(response.getStatusLine().getStatusCode(),
					"VS creation did not complete normally");
		}
		service.getPackedVirtualService().deleteOnExit();
	}

	/**
	 * @param service
	 * @throws IOException
	 */
	public void unDeployService(VirtualService service) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpDelete delete = new HttpDelete(String.format(GET_DELETE_PUT_VS_URI, getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName(), service.getDeployedName()));
		delete.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64(new String(userName + ":" + password).getBytes()))));

		HttpResponse response = httpClient.execute(delete);
		if (LOG.isDebugEnabled()) {
			LOG.debug("UnDeploying service " + service.getDeployedName() + "....");
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("Server Response Code :" + response.getStatusLine().getStatusCode());
			LOG.debug("Server respond :" + responseString);
		}
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.error("Server Response Code :" + response.getStatusLine().getStatusCode());
			LOG.error("Server respond :" + responseString);
			// throw new
			// HttpResponseException(response.getStatusLine().getStatusCode(),
			// "VS delete did not complete normally");
		}

	}

	/**
	 * @param service
	 * @throws IOException
	 */
	public void changeExecutionMode(VirtualService service) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPut put = new HttpPut(String.format(GET_DELETE_PUT_VS_URI, getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName(), service.getDeployedName()));
		put.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64(new String(userName + ":" + password).getBytes()))));
		put.addHeader("Content-Type", "application/vnd.ca.lisaInvoke.virtualService+xml");
		String payload = service.buildExcusionModePayload();
		StringEntity param = new StringEntity(payload);

		put.setEntity(param);
		HttpResponse response = httpClient.execute(put);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Changing Excution service Mode" + service.getDeployedName() + "....");

			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("Server Response Code :" + response.getStatusLine().getStatusCode());
			LOG.debug("Server respond :" + responseString);
		}
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.error("Server Response Code :" + response.getStatusLine().getStatusCode());
			LOG.error("Server respond :" + responseString);
			// throw new
			// HttpResponseException(response.getStatusLine().getStatusCode(),
			// "VS delete did not complete normally");
		}

	}

	/**
	 * 
	 * @throws IOException
	 */
	public List<VirtualService> listVirtualServices() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		List<VirtualService> virtualServices = new ArrayList<VirtualService>();
		HttpGet get = new HttpGet(
				String.format(GET_SERVICES_URI, getRegistryHostName(), SvAsCodeConfigUtil.registryPort()));
		get.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64(new String(userName + ":" + password).getBytes()))));
		get.addHeader("Accept", "application/vnd.ca.lisaInvoke.vseList+json");
		HttpResponse response = httpClient.execute(get);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			ReadContext ctx = JsonPath.parse(responseString);
			String expression = String.format(GET_SERVICE_PATH, getName());
			List<String> services = ctx.read(expression);
			VirtualService vs = null;
			for (String service : services) {
				vs = new VirtualService(service, this);
				vs.setDeployedName(service);
				virtualServices.add(vs);
			}

		}
		return virtualServices;
	}
	
	/**
	 * @throws IOException
	 */
	public void cleanServer() throws IOException {
		
		List<VirtualService> virtualServices = listVirtualServices();
		LOG.info("Cleaning VSE <"+getName()+">..."+virtualServices.size()+" services found...");
		for (VirtualService virtualService : virtualServices) {
			virtualService.unDeploy();
		}
		LOG.info("VSE <"+getName()+"> cleaned up!");
	}

	/**
	 * @param service
	 * @throws IOException
	 */
	public boolean exist(VirtualService service) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(String.format(GET_DELETE_PUT_VS_URI, getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName(), service.getDeployedName()));
		get.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64(new String(userName + ":" + password).getBytes()))));
		get.addHeader("Content-Type", "application/vnd.ca.lisaInvoke.virtualService+xml");

		HttpResponse response = httpClient.execute(get);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Get Service from Server " + service.getDeployedName() + "....");

			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("Server Response Code :" + response.getStatusLine().getStatusCode());
			LOG.debug("Server respond :" + responseString);
		}
		return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;

	}
}

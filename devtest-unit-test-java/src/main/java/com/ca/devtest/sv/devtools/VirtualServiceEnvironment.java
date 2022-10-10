package com.ca.devtest.sv.devtools;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import com.ca.devtest.sv.devtools.annotation.v3.VirtualServiceV3Type;
import com.ca.devtest.sv.devtools.services.AbstractVirtualService;
import com.ca.devtest.sv.devtools.services.v3.VirtualServiceV3;
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
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.utils.SvAsCodeConfigUtil;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import javax.net.ssl.SSLContext;

public class VirtualServiceEnvironment {

	public static final String HTTP_PROTOCOL = "http";
	public static final String HTTPS_PROTOCOL = "https";

	private String name;
	private final String registryHostName;
	private final String group;
	private final String userName;
	private final String password;
	private final String protocol;
	private final String keystore;
	private final String keystorePassword;

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	protected static final String GET_DELETE_PUT_VS_URI = "%s://%s:%s/api/Dcm/VSEs/%s/%s";
	protected static final String GET_SERVICES_URI = "%s://%s:%s/api/Dcm/VSEs";
	protected static final String GET_SERVICE_PATH = "$.vseList[?(@.name=='%s')]..virtualServiceList[*].name";

	public VirtualServiceEnvironment(String protocol, String registryHostName, String name, String userName, String password,
			String group, String keystore, String keystorePassword) {
		super();
		this.name = SvAsCodeConfigUtil.deployServiceToVse(name);
		this.group = SvAsCodeConfigUtil.group(group);
		this.registryHostName = SvAsCodeConfigUtil.registryHost(registryHostName);
		this.userName = SvAsCodeConfigUtil.login(userName);
		this.password = SvAsCodeConfigUtil.password(password);
		this.protocol = SvAsCodeConfigUtil.protocol(protocol);
		if(this.protocol== null || (!this.protocol.equals(HTTPS_PROTOCOL) && !this.protocol.equals(HTTP_PROTOCOL))){
			throw new IllegalArgumentException("Protocol cannot be null and should be '"+HTTPS_PROTOCOL+"' or '"+HTTP_PROTOCOL+"'");
		}
		this.keystore = SvAsCodeConfigUtil.keystore(keystore);
		this.keystorePassword = SvAsCodeConfigUtil.keystorePassword(keystorePassword);
	}

	public VirtualServiceEnvironment() {
		super();
		this.name = SvAsCodeConfigUtil.deployServiceToVse(null);
		this.group = SvAsCodeConfigUtil.group(null);
		this.registryHostName = SvAsCodeConfigUtil.registryHost(null);
		this.userName = SvAsCodeConfigUtil.login(null);
		this.password = SvAsCodeConfigUtil.password(null);
		this.protocol = SvAsCodeConfigUtil.protocol(null);
		this.keystore = SvAsCodeConfigUtil.keystore(null);
		this.keystorePassword = SvAsCodeConfigUtil.keystorePassword(null);

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

	public String getProtocol() { return protocol; }

	public String getKeyStore() {return keystore;}

	public String getKeystorePassword() { return keystorePassword;}

	public void addParam(MultipartEntityBuilder builder, String paramName, String value, boolean isFile){
		if(value != null && !value.isEmpty()){
			if(isFile) {
				FileBody fileParam = new FileBody(new File(value), ContentType.APPLICATION_JSON);
				builder.addPart(paramName, fileParam);
			}else {
				StringBody strParam = new StringBody(value, ContentType.APPLICATION_JSON);
				builder.addPart(paramName, strParam);
			}
		}
	}

	/**
	 * @param abstractVirtualService
	 * @throws IOException
	 */
	public void deployService(AbstractVirtualService abstractVirtualService) throws IOException, NoSuchAlgorithmException,
			KeyManagementException, CertificateException, KeyStoreException {

		if(SvAsCodeConfigUtil.undeployIfExist()
				&& abstractVirtualService.getType().equals(VirtualServiceV3Type.CREATE.getType())
				&& exist(abstractVirtualService)){
			unDeployService(abstractVirtualService);
		}
		HttpClient httpClient = createHttpClient();

		String urlPost = String.format(abstractVirtualService.getUrl(),getProtocol(), getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName(), abstractVirtualService.getDeployedName());

		HttpPost post = new HttpPost(urlPost);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if(abstractVirtualService instanceof VirtualServiceV3) {
			VirtualServiceV3 service = (VirtualServiceV3)abstractVirtualService;
			addParam(builder, "inputFile1", service.getInputFile1(),true);
			addParam(builder, "inputFile2", service.getInputFile2(),true);
			addParam(builder, "dataFile", service.getDataFile(),true);
			addParam(builder, "activeConfig", service.getActiveConfig(),true);
			addParam(builder, "swaggerurl", service.getSwaggerurl(),false);
			addParam(builder, "wadlurl", service.getWadlurl(),false);
			addParam(builder, "ramlurl", service.getRamlurl(),false);
			StringBody config = new StringBody(service.getConfig(), ContentType.APPLICATION_JSON);
			builder.addPart("config", config);
			StringBody deploy = new StringBody("true", ContentType.APPLICATION_JSON);
			builder.addPart("deploy", deploy);
			post.setHeader("Accept", "application/json");
		}else {
			VirtualService service = (VirtualService) abstractVirtualService;
			FileBody contentBody = new FileBody(service.getPackedVirtualService(), ContentType.APPLICATION_JSON);
			builder.addPart("file", contentBody);
		}
		post.setEntity(builder.build());

		post.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64((userName + ":" + password).getBytes()))));

		HttpResponse response = httpClient.execute(post);

		LOG.info("Deploying service " + abstractVirtualService.getDeployedName() + "....");
		HttpEntity entity = response.getEntity();

		LOG.info("Server Response Code :" + response.getStatusLine().getStatusCode());
		if (LOG.isDebugEnabled()) {
			String responseString = EntityUtils.toString(entity, "UTF-8");
			LOG.debug("Server respond :" + responseString);
		}
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED && !(abstractVirtualService instanceof VirtualServiceV3)) {

			LOG.error("Error deploying service :" + abstractVirtualService.getDeployedName());
			throw new HttpResponseException(response.getStatusLine().getStatusCode(),
					"VS creation did not complete normally");
		}else if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK &&  abstractVirtualService instanceof VirtualServiceV3){
			LOG.error("Error deploying service :" + abstractVirtualService.getDeployedName());
			throw new HttpResponseException(response.getStatusLine().getStatusCode(),
					"VS creation did not complete normally");
		}
		abstractVirtualService.clean();
	}

	/**
	 * @param service
	 * @throws IOException
	 */
	public void unDeployService(AbstractVirtualService service) throws IOException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

		HttpClient httpClient = createHttpClient();
		HttpDelete delete = new HttpDelete(String.format(GET_DELETE_PUT_VS_URI, getProtocol(), getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName(), service.getDeployedName()));
		delete.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64((userName + ":" + password).getBytes()))));

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
	public void changeExecutionMode(VirtualService service) throws IOException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		HttpClient httpClient = createHttpClient();
		HttpPut put = new HttpPut(String.format(GET_DELETE_PUT_VS_URI, getProtocol(), getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName(), service.getDeployedName()));
		put.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64((userName + ":" + password).getBytes()))));
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
	public List<AbstractVirtualService> listVirtualServices() throws IOException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		HttpClient httpClient = createHttpClient();
		List<AbstractVirtualService> virtualServices = new ArrayList<AbstractVirtualService>();
		HttpGet get = new HttpGet(
				String.format(GET_SERVICES_URI, getProtocol(), getRegistryHostName(), SvAsCodeConfigUtil.registryPort()));
		get.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64((userName + ":" + password).getBytes()))));
		get.addHeader("Accept", "application/vnd.ca.lisaInvoke.vseList+json");
		HttpResponse response = httpClient.execute(get);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			ReadContext ctx = JsonPath.parse(responseString);
			String expression = String.format(GET_SERVICE_PATH, getName());
			List<String> services = ctx.read(expression);
			AbstractVirtualService vs = null;
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
	public void cleanServer() throws IOException, CertificateException, NoSuchAlgorithmException,
			KeyStoreException, KeyManagementException {
		
		List<AbstractVirtualService> virtualServices = listVirtualServices();
		LOG.info("Cleaning VSE <"+getName()+">..."+virtualServices.size()+" services found...");
		for (AbstractVirtualService virtualService : virtualServices) {
			virtualService.unDeploy();
		}
		LOG.info("VSE <"+getName()+"> cleaned up!");
	}

	/**
	 * @param service
	 * @throws IOException
	 */
	public boolean exist(AbstractVirtualService service) throws IOException, CertificateException,
			NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		HttpClient httpClient = createHttpClient();
		HttpGet get = new HttpGet(String.format(GET_DELETE_PUT_VS_URI, getProtocol(), getRegistryHostName(),
				SvAsCodeConfigUtil.registryPort(), getName(), service.getDeployedName()));
		get.setHeader("Authorization", String.format("Basic %s",
				new String(Base64.encodeBase64((userName + ":" + password).getBytes()))));
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

	private CloseableHttpClient createHttpClient() throws CertificateException, NoSuchAlgorithmException,
			KeyStoreException, IOException, KeyManagementException {
		if(getProtocol().equals(HTTPS_PROTOCOL)) {
			SSLContextBuilder SSLBuilder = SSLContexts.custom();
			File file = new File(getKeyStore());
			SSLBuilder = SSLBuilder.loadTrustMaterial(file, getKeystorePassword().toCharArray());
			SSLContext sslContext = SSLBuilder.build();
			SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			HttpClientBuilder clientbuilder = HttpClients.custom();
			clientbuilder = clientbuilder.setSSLSocketFactory(sslConSocFactory);
			return clientbuilder.build();
		}else{
			return HttpClients.createDefault();
		}
	}
}

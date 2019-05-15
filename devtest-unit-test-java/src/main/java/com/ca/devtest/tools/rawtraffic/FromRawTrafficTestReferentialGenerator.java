package com.ca.devtest.tools.rawtraffic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FromRawTrafficTestReferentialGenerator {

	private static final String RAWFILETEMPLATE_TEXT = "%s-%d-%s.txt";
	private static final String RAWFILETEMPLATE_XML = "%s-%d-%s.xml";
	private static final String TEMPLATE_META_LINE = "%s: %s\r\n";
	private static final String METAFILETEMPLATE_XML = "%s-%d-%s.properties";
	private static final String TEMPLATE_REPORT = "%s;%s";
	private static final String TEMPLATE_SERVICE_CATALOGUE_COL_NAME = "SERVICENAME;ENDPOINT;RRPairs;port";
	private static final String TEMPLATE_SERVICE_CATALOGUE = "%s;%s;%s;50000";
	private static final String TEMPLATE_TEST_CATALOG_COL_NAME = "ServiceName;EndPoint;TargetServer;TargetPort;LisaPort;RRPairsFolder;RequestFile;ResponseFile";
	private static final String TEMPLATE_TEST_CATALOG = "%s;%s;%s;%s;50000;%s;%s;%s";
	private static final boolean GENERATE_META = false;

	private static final String ROOTFOLDER = "rrpairs";

	private static final XPath XPATH = XPathFactory.newInstance().newXPath();
	private static final List<String> listofMetaToRejet = new ArrayList<String>();
	static {
		listofMetaToRejet.add("HTTP-Response-Code");
		listofMetaToRejet.add("HTTP-Response-Code-Text");
		listofMetaToRejet.add("Server");
		listofMetaToRejet.add("Date");
		listofMetaToRejet.add("X-Powered-By");
		listofMetaToRejet.add("X-Requested-With");
		listofMetaToRejet.add("Accept");
		listofMetaToRejet.add("Host");

	}

	public static void main(String[] args)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		if (args.length == 0) {
			System.err.println("please specify the raw traffic file location");
			System.exit(1);
		}
		String rawFolder = args[0];
		generateRRPairFromRawFiles(rawFolder);
	}

	/**
	 * @param args
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static void generateRRPairFromRawFiles(String rawFileFolder)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		File rawFolderFile = new File(rawFileFolder);
		File[] files = rawFolderFile.listFiles();
		List<String> auditList = new ArrayList<String>();
		List<String> testCatalogList = new ArrayList<String>();
		// list of service to build the CSV file for service generation
		// the line will be serviceName;endpoints;directory of RRpairs;port
		Set<String> listOfServices = new HashSet<String>();

		for (File rawFile : files) {
			if (rawFile.getName().contains(".xml"))

				try {

					DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
					domFactory.setNamespaceAware(true);
					DocumentBuilder builder = domFactory.newDocumentBuilder();
					Document doc = builder.parse(rawFile);

					// XPath Query for showing all nodes value
					XPathExpression expr = XPATH.compile("//transaction");
					XPathExpression exprRequest = XPATH.compile("request/body/text()");
					XPathExpression exprRequestmeta = XPATH.compile("request/metaData");

					Object result = expr.evaluate(doc, XPathConstants.NODESET);
					XPathExpression itemResponseExpr = XPATH.compile("responses/response/body/text()");
					XPathExpression exprResponsetmeta = XPATH.compile("responses/response/metaData");
					NodeList nodes = (NodeList) result;
					String reqorres = null;

					int indice = 1;

					String folderName = null;
					File folderStore = null;
					String audit = null;

					String serviceCalatogeLine = null;
					File requestFile = null;
					File responseFile = null;
					String testCalatogeLine = null;
					String targetServer = null;
					String scenarioName = rawFile.getName().replace(".xml", "");
					String parameters = null;
					String endpoint = null;
					for (int i = 0; i < nodes.getLength(); i++) {

						Node item = (Node) nodes.item(i);
						Object request = exprRequest.evaluate(item, XPathConstants.NODESET);
						Object response = itemResponseExpr.evaluate(item, XPathConstants.NODESET);
						Node requestItem = (Node) ((NodeList) request).item(0);

						folderName = getOperationName(item);

						parameters = getParameters(item);
						endpoint = parameters.length() > 0 ? folderName + "?" + parameters : folderName;
						targetServer = getHost(item);

						folderStore = new File(rawFile.getParentFile(), ROOTFOLDER + folderName);
						folderStore.mkdirs();
						Node requestMeta = (Node) exprRequestmeta.evaluate(item, XPathConstants.NODE);
						reqorres = "req";
						if (isBinary(requestItem)) {

							writeBeforeDecodeBase64(requestItem, scenarioName, indice, reqorres, folderStore);
						} else {
							requestFile = writeBeforeDecodeXML(requestItem, requestMeta, scenarioName, indice, reqorres,
									folderStore);
						}
						// treat meta-data of request
						/*
						 * Object requestMeta = exprRequestmeta.evaluate(item,
						 * XPathConstants.NODESET); Node requestMetaItem =
						 * (Node) ((NodeList) requestMeta) .item(0);
						 * writeMetaData(requestMetaItem, scenarioName, indice,
						 * "meta-req", folderStore);
						 */
						// treat the response Node
						Node itemResponse = (Node) ((NodeList) response).item(0);

						// treat meta-data of response
						Object responseMeta = exprResponsetmeta.evaluate(item, XPathConstants.NODESET);
						Node responseMetaItem = (Node) ((NodeList) responseMeta).item(0);
						reqorres = "rsp";
						if (isBinary(itemResponse)) {

							writeBeforeDecodeBase64(itemResponse, scenarioName, indice, reqorres, folderStore);
						} else {
							responseFile = writeBeforeDecodeXML(itemResponse, null, scenarioName, indice,
									reqorres, folderStore);
						}

						// comming from meta-rsp
						if (GENERATE_META)
							writeMetaData(responseMetaItem, scenarioName, indice, "rsp-meta", folderStore);
						// Add line in test catalogue
						String requestFileName = folderName + "/" + requestFile.getName();
						String responseFileName = folderName + "/" + responseFile.getName();

						String[] infos = targetServer.split(":");
						String server = infos[0];
						String port = "80";
						if (infos.length > 1) {
							port = infos[1];
						}

						testCalatogeLine = String.format(TEMPLATE_TEST_CATALOG, folderStore.getName(), endpoint, server,
								port, ROOTFOLDER, requestFileName, responseFileName);
						testCatalogList.add(testCalatogeLine);

						indice++;

					}

					// generate Test Catalog
					generateTestCatalog(testCatalogList, rawFile.getParentFile());

				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Error during handle raw file");
					System.exit(1);
				}
		}
		System.out.println("DONE...");
	}

	private static void generateTestCatalog(Collection<String> listOfServices, File inFolder) throws IOException {
		Collection<String> lines = new ArrayList<String>();
		lines.add(TEMPLATE_TEST_CATALOG_COL_NAME);
		lines.addAll(listOfServices);
		writeReport(lines, inFolder, ROOTFOLDER + "/testCatalog.csv");

	}

	private static void generateServiceCatalog(Set<String> listOfServices, File inFolder) throws IOException {
		Collection<String> lines = new ArrayList<String>();
		lines.add(TEMPLATE_SERVICE_CATALOGUE_COL_NAME);
		lines.addAll(listOfServices);
		writeReport(lines, inFolder, "sevicesCatalogue.csv");

	}

	private static File writeMetaData(Node metaItem, String scenarioName, int indice, String reqorres, File folderStore)
			throws XPathExpressionException {

		NodeList parameters = metaItem.getChildNodes();
		StringBuilder metaProperties = new StringBuilder();
		for (int i = 0; i < parameters.getLength(); i++) {

			Node item = (Node) parameters.item(i);
			String name = XPATH.evaluate("@name", item);
			String value = XPATH.evaluate("text()", item);
			if (!StringUtils.isBlank(name) && !listofMetaToRejet.contains(name.trim()))
				metaProperties.append((String.format(TEMPLATE_META_LINE, name, value)));
		}
		return writeInFile(metaProperties.toString().getBytes(), indice, scenarioName, METAFILETEMPLATE_XML, reqorres,
				folderStore);
	}

	/**
	 * @param item
	 * @param indice
	 * @param reqorres
	 * @param directory
	 */
	private static File writeBeforeDecodeBase64(Node item, String nameofScenario, int indice, String reqorres,
			File directory) {

		return writeInFile(Base64.decodeBase64(item.getNodeValue().getBytes()), indice, nameofScenario,
				RAWFILETEMPLATE_TEXT, reqorres, directory);
	}

	/**
	 * @param item
	 * @param indice
	 * @param reqorres
	 * @param directory
	 * @throws XPathExpressionException
	 */
	private static File writeBeforeDecodeXML(Node item, Node metaData, String nameofScenario, int indice,
			String reqorres, File directory) throws XPathExpressionException {
		String content = "";
		if( null!=item){
			content=item.getNodeValue().replace(System.getProperty("line.separator"), "");
		}
		String message =null;
		if(null!=metaData){
			String header = handleMetaData(metaData);
			 message = String.format(header, content);
		}else{
			 message =content;
		}
		
		return writeInFile(message.getBytes(), indice, nameofScenario,
				RAWFILETEMPLATE_XML, reqorres, directory);

	}

	private static String handleMetaData(Node metaItem) throws XPathExpressionException {
		NodeList parameters = metaItem.getChildNodes();
		StringBuilder metaProperties = new StringBuilder(" ");
		String method = "";
		String uri = "";
		String version = "";
		String contentType = "";
		for (int i = 0; i < parameters.getLength(); i++) {

			Node item = (Node) parameters.item(i);
			String name = XPATH.evaluate("@name", item);
			String value = XPATH.evaluate("text()", item);
			if (!StringUtils.isBlank(name)) {
				if ("HTTP-Method".equals(name)) {
					method = value;
					continue;
				}
				if ("HTTP-URI".equals(name)) {
					uri = value;
					continue;
				}
				if ("HTTP-Version".equals(name)) {
					version = value;
					continue;
				}
				if ("Content-Type".equals(name)) {
					contentType = value;
					
				}

			}
			
			if (!StringUtils.isBlank(name) && !listofMetaToRejet.contains(name.trim()))
				metaProperties.append((String.format(TEMPLATE_META_LINE, name, value)));
		}
		
		// analyse contentType to define right template 
		StringBuilder meta = new StringBuilder();
		
		if(contentType.contains("x-www-form-urlencoded")){
			meta.append(method).append(" ").append(uri).append("?%s ").append(version).append("\n")
			.append(metaProperties.toString());
		}else{
			meta.append(method).append(" ").append(uri).append(" ").append(version).append("\n")
			.append(metaProperties.toString()).append("\n%s");
		}
		
				
		

		return meta.toString();
	}

	private static void writeReport(Collection<String> auditList, File parent, String fileName) throws IOException {

		FileUtils.writeLines(new File(parent, fileName), auditList);

	}

	/**
	 * @param data
	 * @param indice
	 * @param reqorres
	 * @param directory
	 */
	private static File writeInFile(byte[] data, int indice, String scenarioName, String templateName, String reqorres,
			File directory) {
		String fileName = String.format(templateName, scenarioName, indice, reqorres);
		System.out.println("Creating file :" + fileName);
		OutputStream os = null;
		File result = new File(directory, fileName);
		try {

			os = new FileOutputStream(result);
			os.write(data);
			os.flush();

		} catch (Exception e) {

		} finally {
			try {
				os.close();
			} catch (Exception e2) {
			}
		}
		return result;
	}

	/**
	 * @param item
	 * @return
	 * @throws XPathExpressionException
	 */
	private static boolean isBinary(Node item) throws XPathExpressionException {
		// String value = XPATH.evaluate("//@binary", item);

		// return null != value && Boolean.valueOf(value);
		return false;

	}

	/**
	 * @param endpoint
	 * @return
	 */
	private static String getTransactionName(String endpoint) {

		if (endpoint.startsWith("/")) {
			endpoint = endpoint.substring(1);
		}
		if (endpoint.endsWith("/")) {
			endpoint = endpoint.substring(0, endpoint.length() - 1);
		}
		endpoint = endpoint.replaceAll("/", "-");
		return endpoint;
	}

	/**
	 * @param item
	 * @return
	 * @throws XPathExpressionException
	 */
	private static String getOperationName(Node requestNode) throws XPathExpressionException {
		XPathExpression exprRequestmeta = XPATH.compile("request/metaData/parameter");
		NodeList parameters = (NodeList) exprRequestmeta.evaluate(requestNode, XPathConstants.NODESET);

		for (int i = 0; i < parameters.getLength(); i++) {

			Node item = (Node) parameters.item(i);
			String name = XPATH.evaluate("@name", item);
			String value = XPATH.evaluate("text()", item);
			if ("HTTP-URI".equalsIgnoreCase(name)) {
				return value;
			}
		}
		return null;

	}

	private static String getParameters(Node requestNode) throws XPathExpressionException {
		XPathExpression exprRequestParameter = XPATH.compile("request/arguments/parameter");
		NodeList parameters = (NodeList) exprRequestParameter.evaluate(requestNode, XPathConstants.NODESET);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parameters.getLength(); i++) {

			Node item = (Node) parameters.item(i);
			String name = XPATH.evaluate("@name", item);
			String value = XPATH.evaluate("text()", item);
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(name).append("=").append(value);

		}
		return sb.toString();
	}

	/**
	 * @param item
	 * @return
	 * @throws XPathExpressionException
	 */
	private static String getHost(final Node requestNode) throws XPathExpressionException {
		XPathExpression exprRequestmeta = XPATH.compile("request/metaData/parameter");
		NodeList parameters = (NodeList) exprRequestmeta.evaluate(requestNode, XPathConstants.NODESET);

		for (int i = 0; i < parameters.getLength(); i++) {

			Node item = (Node) parameters.item(i);
			String name = XPATH.evaluate("@name", item);
			String value = XPATH.evaluate("text()", item);
			if ("Host".equalsIgnoreCase(name)) {
				return value;
			}
		}
		return null;

	}
}

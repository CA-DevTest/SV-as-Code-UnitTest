package com.ca.devtest.tools.rawtraffic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author gaspa03
 * 
 */
public class RawTrafficTransactionGouper {

	private static final XPath XPATH = XPathFactory.newInstance().newXPath();
	private static final String SHORT_MESSAGE_LOG_ENDPOINT = "%s=%d";
	private static final String GENERATED_FOLDERNAME = "generated";
	private static final String SUM_MESSAGE_LOG_ENDPOINT = "# Endpoint =%d #transaction=%d";
	private static final String GROUP_ENDPOINT = new String(
			"DomaineDR7CandidatServiceSync,,");

	private static final String TRANSCATION_FILENAME_PAT = "%s.xml";
	private static final String SERVICE_LINE_HEADER = "serviceName;targetServer;targetPort;endpointPath;vsport;vsmName,vsmType";
	private static final String SERVICE_LINE_FORMAT = "%s;%s;%s;%s;50000;%s;%s";
	private static final String SERVICE_REPORT_FILENAME = "00-servicesCatalog.csv";

	private static final String LISA_VSM_NAME = "lisa.vsm.%s.name=%s";
	private static final String LISA_VSM_TYPE = "lisa.vsm.%s.type=%s";

	public static void main(String[] args) {

		if (args.length == 0) {
			System.err.println("please specify the raw traffic file location");
			System.exit(1);
		}
		String rawFolder = args[0];
		generateGroupRawTrafficByTransaction(rawFolder, GROUP_ENDPOINT);
	}

	/**
	 * @param rawFolderPath
	 */
	public static void generateGroupRawTrafficByTransaction(
			String rawFolderPath, String groupedEndPoint) {

		try {
			File rawFolder = new File(rawFolderPath);
			Collection<File> rawTrafficFiles = FileUtils.listFiles(rawFolder,
					new String[] { "xml" }, false);

			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder;

			builder = domFactory.newDocumentBuilder();
			Map<String, List<Node>> sortedTransactions = new HashMap<String, List<Node>>();
			for (File rawFile : rawTrafficFiles) {

				Document doc = builder.parse(rawFile);

				// XPath Query for showing all nodes value
				XPathExpression transactionExpr = XPATH
						.compile("//transaction");

				NodeList transactions = (NodeList) transactionExpr.evaluate(
						doc, XPathConstants.NODESET);
				for (int i = 0; i < transactions.getLength(); i++) {

					Node transaction = (Node) transactions.item(i);

					addTransaction(sortedTransactions,
							getEndPoint(transaction), transaction,
							groupedEndPoint);
				}
			}

			print(sortedTransactions, false);
			generateRawTrafficGrouped(builder, rawFolder, sortedTransactions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param rawFolder
	 * @param sortedTransactions
	 * @throws XPathExpressionException
	 * @throws IOException
	 */
	private static void generateRawTrafficGrouped(DocumentBuilder builder,
			File rawFolder, Map<String, List<Node>> sortedTransactions)
			throws XPathExpressionException, IOException {

		Set<String> keys = sortedTransactions.keySet();
		List<Node> transactions = null;
		Collection<String> servicesList = new HashSet<String>();
		for (String endpoint : keys) {
			transactions = sortedTransactions.get(endpoint);
			generateRawTraffic(builder, servicesList, rawFolder, endpoint,
					transactions);
		}
		generateServicesRepport(servicesList, rawFolder);

	}

	/**
	 * @param servicesList
	 * @param rawFolder
	 * @throws IOException
	 */
	private static void generateServicesRepport(
			Collection<String> servicesList, File rawFolder) throws IOException {
		Collection<String> lines = new ArrayList<String>();
		lines.add(SERVICE_LINE_HEADER);
		lines.addAll(servicesList);
		FileUtils.writeLines(new File(getGeneratedFolder(rawFolder),
				SERVICE_REPORT_FILENAME), lines);

	}

	/**
	 * @param servicesList
	 * @param rawFolder
	 * @param endpoint
	 * @param transactions
	 * @throws XPathExpressionException
	 */
	private static void generateRawTraffic(DocumentBuilder builder,
			Collection<String> servicesList, File rawFolder, String endpoint,
			List<Node> transactions) throws XPathExpressionException {
		try {

			// root elements
			Document doc = builder.newDocument();
			Element rootElement = doc.createElement("rawTraffic");
			doc.appendChild(rootElement);

			String transactionName = getTransactionName(endpoint);
			File folderGenerated = getGeneratedFolder(rawFolder);

			File transactionFile = new File(folderGenerated, String.format(
					TRANSCATION_FILENAME_PAT, transactionName));

			for (Node transaction : transactions) {
				rootElement.appendChild(doc.adoptNode(transaction));
				// transaction info in list of transcation
				servicesList.add(getServiceLine(transaction, transactionName,
						endpoint));

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(transactionFile);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

	/**
	 * @param rawFolder
	 * @return
	 */
	private static File getGeneratedFolder(File rawFolder) {
		File folderGenerated = new File(rawFolder, GENERATED_FOLDERNAME);
		folderGenerated.mkdirs();
		return folderGenerated;
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

	private static String getServiceLine(Node transaction,
			String transactionName, String endpoint)
			throws XPathExpressionException {
		String host = getHost(transaction);
		String[] infos = host.split(":");
		String server = infos[0];
		String port = "80";
		if (infos.length > 0) {
			port = infos[1];
		}
		String lisaVsmName = String.format(LISA_VSM_NAME, transactionName,transactionName);
		String lisaVsmType = String.format(LISA_VSM_TYPE,transactionName ,"SOAP");
		
		return String.format(SERVICE_LINE_FORMAT, transactionName, server,
				port, endpoint, lisaVsmName,lisaVsmType);
	}

	/**
	 * @param sortedTransactions
	 * @param deep
	 */
	private static void print(Map<String, List<Node>> sortedTransactions,
			boolean deep) {

		Set<String> keys = sortedTransactions.keySet();
		List<Node> transactions = null;
		int transactionCounter = 0;
		int nbEndpoints = 0;
		int nbTransaction = 0;

		for (String endpoint : keys) {
			transactions = sortedTransactions.get(endpoint);

			nbTransaction = transactions.size();
			transactionCounter = transactionCounter + nbTransaction;
			nbEndpoints++;
			if (!deep) {
				System.out.println(String.format(SHORT_MESSAGE_LOG_ENDPOINT,
						endpoint, nbTransaction));
			}

		}
		System.out.println(String.format(SUM_MESSAGE_LOG_ENDPOINT, nbEndpoints,
				transactionCounter));
	}

	/**
	 * @param sortedTransactions
	 * @param endPoint
	 * @param transaction
	 */
	private static void addTransaction(
			Map<String, List<Node>> sortedTransactions, String endPoint,
			Node transaction, String groupedEnpoint) {
		List<Node> rawTrafficList = null;
		endPoint = getEndpointGroupeName(endPoint, groupedEnpoint);
		if (sortedTransactions.containsKey(endPoint)) {
			rawTrafficList = sortedTransactions.get(endPoint);
		} else {

			rawTrafficList = new ArrayList<Node>();
			sortedTransactions.put(endPoint, rawTrafficList);

		}

		rawTrafficList.add(transaction);
	}

	/**
	 * @param endPoint
	 * @return
	 */
	private static String getEndpointGroupeName(String endPoint,
			String groupedEnpoint) {
		String endpointName = endPoint;
		String[] group = groupedEnpoint.split(",");
		for (String groupeName : group) {
			if (groupeName.length()>0&&endPoint.startsWith(groupeName)) {
				endpointName = groupeName;
				break;
			}

		}
		return endpointName;

	}

	/**
	 * @param item
	 * @return
	 * @throws XPathExpressionException
	 */
	private static String getEndPoint(Node requestNode)
			throws XPathExpressionException {
		XPathExpression exprRequestmeta = XPATH
				.compile("request/metaData/parameter");
		NodeList parameters = (NodeList) exprRequestmeta.evaluate(requestNode,
				XPathConstants.NODESET);

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

	/**
	 * @param item
	 * @return
	 * @throws XPathExpressionException
	 */
	private static String getHost(final Node requestNode)
			throws XPathExpressionException {
		XPathExpression exprRequestmeta = XPATH
				.compile("request/metaData/parameter");
		NodeList parameters = (NodeList) exprRequestmeta.evaluate(requestNode,
				XPathConstants.NODESET);

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

package com.ca.devtest.sv.devtools.v3;

import org.apache.http.HttpEntity;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

public class XMLResponseParser implements ResponseParser{
    Document document;
    private static final XPath XPATH = XPathFactory.newInstance().newXPath();

    public XMLResponseParser(HttpEntity httpEntity){
        parse(httpEntity);
    }
    @Override
    public void parse(HttpEntity httpEntity) {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            document = builder.parse(httpEntity.getContent());
        }catch (Exception e){
            System.out.print(e);
        }
    }

    @Override
    public String getValue(String path) {
        if(document == null || path == null){
            return null;
        }
        try {
            XPathExpression expr = XPATH.compile(path);
            Object result = expr.evaluate(document, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            return nodes.item(0).getFirstChild().getNodeValue();
        }catch (Exception e){
            return null;
        }
    }
}

package com.ca.devtest.sv.devtools.v3;

import org.apache.http.HttpEntity;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public interface ResponseParser {
    void parse(HttpEntity respose) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException;
    String getValue(String path);
}

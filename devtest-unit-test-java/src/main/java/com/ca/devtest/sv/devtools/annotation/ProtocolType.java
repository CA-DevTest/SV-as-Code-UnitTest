package com.ca.devtest.sv.devtools.annotation;

/**
 * @author gaspa03
 *
 */
public interface ProtocolType {

	//public String DPH_DOIT= "com.ca.devtest.dph.DoItDataHandler";
    String DPH_SOAP = "com.itko.lisa.vse.stateful.protocol.ws.WSSOAPProtocolHandler";
	String TPH_HTTP = "com.itko.lisa.vse.stateful.protocol.http.HttpProtocolHandler";
	//public String TPH_SWEETDEV="com.ca.devtest.extension.protocol.sweetdev.SweetDevRRTransportProtocol";
    String DPH_REST = "com.itko.lisa.vse.stateful.protocol.rest.RestDataProtocol";
	String DPH_XML = "com.itko.lisa.vse.stateful.protocol.xml.XMLDataProtocol";
	String DPH_JSON = "com.itko.lisa.vse.stateful.protocol.json.JSONDataProtocolHandler";

}
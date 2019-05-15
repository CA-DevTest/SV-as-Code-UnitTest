package com.ca.devtest.sv.devtools.annotation;

/**
 * @author gaspa03
 *
 */
public interface ProtocolType {

	//public String DPH_DOIT= "com.ca.devtest.dph.DoItDataHandler";
	public String DPH_SOAP = "com.itko.lisa.vse.stateful.protocol.ws.WSSOAPProtocolHandler";
	public String TPH_HTTP = "com.itko.lisa.vse.stateful.protocol.http.HttpProtocolHandler";
	//public String TPH_SWEETDEV="com.ca.devtest.extension.protocol.sweetdev.SweetDevRRTransportProtocol";
	public String DPH_REST = "com.itko.lisa.vse.stateful.protocol.rest.RestDataProtocol";
	public String DPH_XML = "com.itko.lisa.vse.stateful.protocol.xml.XMLDataProtocol";
	public String DPH_JSON = "com.itko.lisa.vse.stateful.protocol.json.JSONDataProtocolHandler";

}
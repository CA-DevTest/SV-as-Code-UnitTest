/**
 * 
 */
package com.ca.devtest.sv.devtools.type;

/**
 * @author gaspa03
 *
 */
public enum DataProtocolType {

	//DOIT("com.personalfinance.devtest.dph.DoItDataHandler"),
	SOAP("com.itko.lisa.vse.stateful.protocol.ws.WSSOAPProtocolHandler"),
	COPY_BOOK("com.itko.lisa.vse.stateful.protocol.copybook.CopybookDataProtocol"),
	COPY_BOOK_RRPAIRS("com.ca.devtest.copybook.dph.CopybookRRPairsDatahandler"),
	REST("com.itko.lisa.vse.stateful.protocol.rest.RestDataProtocol"),
	XML("com.itko.lisa.vse.stateful.protocol.xml.XMLDataProtocol"),
	JSON("com.itko.lisa.vse.stateful.protocol.json.JSONDataProtocol");

	
	private String type="";
	
	DataProtocolType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
}

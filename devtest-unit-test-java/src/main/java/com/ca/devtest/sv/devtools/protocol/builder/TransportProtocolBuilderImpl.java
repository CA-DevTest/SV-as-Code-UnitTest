/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ca.devtest.sv.devtools.protocol.DataProtocolDefinition;
import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinition;
import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinitionImpl;

/**
 * @author gaspa03
 *
 */
public class TransportProtocolBuilderImpl implements TransportProtocolBuilder, ParamatrizedBuilder {

	protected final String typebuilder;
	protected Map<String, String> parameters = new HashMap<String, String>();
	private List<DataProtocolDefinition> requestDataProtocol = new ArrayList<DataProtocolDefinition>();
	private List<DataProtocolDefinition> responseDataProtocol = new ArrayList<DataProtocolDefinition>();

	public TransportProtocolBuilderImpl(String type) {
		this.typebuilder = type;
	}

	public TransportProtocolBuilder addParameter(String key, String value) {
		addKeyValue(key, value);

		return this;
	}

	public void addKeyValue(String key, String value) {
		parameters.put(key, value);

	}

	/**
	 * @param type
	 * @return
	 */
	public TransportProtocolBuilderImpl addRequestDataProtocol(DataProtocolBuilder dataProtocolBuilder) {
		requestDataProtocol.add(dataProtocolBuilder.build());
		return this;
	}

	/**
	 * @param type
	 * @return
	 */
	public TransportProtocolBuilder addResponseDataProtocol(DataProtocolBuilder dphBuilder) {
		responseDataProtocol.add(dphBuilder.build());
		return this;
	}

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilder#build()
	 */
	@Override
	public TransportProtocolDefinition build() {

		TransportProtocolDefinitionImpl tph = new TransportProtocolDefinitionImpl(typebuilder);
		tph.setRequestSide(requestDataProtocol);
		tph.setResponseSide(responseDataProtocol);
		tph.getParameters().putAll(parameters);
		return tph;
	}

	

}

package com.ca.devtest.sv.devtools.protocol.builder;

import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinition;

public interface TransportProtocolBuilder {

	/**
	 * @param key
	 * @param value
	 * @return
	 */
    TransportProtocolBuilder addParameter(String key, String value);

	/**
	 * @param dataProtocolBuilder
	 * @return
	 */
    TransportProtocolBuilderImpl addRequestDataProtocol(DataProtocolBuilder dataProtocolBuilder);

	/**
	 * @param dphBuilder
	 * @return
	 */
    TransportProtocolBuilder addResponseDataProtocol(DataProtocolBuilder dphBuilder);

	/**
	 * @return
	 */
	TransportProtocolDefinition build();
}
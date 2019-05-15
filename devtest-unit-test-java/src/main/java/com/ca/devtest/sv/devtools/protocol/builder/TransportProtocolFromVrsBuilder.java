/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol.builder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinition;
import com.ca.devtest.sv.devtools.protocol.TransportProtocolResource;

/**
 * @author gaspa03
 *
 */
public class TransportProtocolFromVrsBuilder implements TransportProtocolBuilder,ParamatrizedBuilder {

	protected Map<String, String> parameters = new HashMap<String, String>();
	private final File resource;

	/**
	 * @param vrsFile
	 */
	public TransportProtocolFromVrsBuilder(File vrsFile) {
		super();

		resource = vrsFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilder#
	 * addParameter(java.lang.String, java.lang.String)
	 */
	public TransportProtocolBuilder addParameter(String key, String value) {
		addKeyValue(key, value);

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilder#
	 * addKeyValue(java.lang.String, java.lang.String)
	 */
	public void addKeyValue(String key, String value) {
		parameters.put(key, value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilder#
	 * build()
	 */
	public TransportProtocolDefinition build() {

		TransportProtocolDefinition tph = new TransportProtocolResource(resource);
		tph.getParameters().putAll(parameters);
		return tph;
	}

	@Override
	public TransportProtocolBuilderImpl addRequestDataProtocol(DataProtocolBuilder dataProtocolBuilder) {
		throw new UnsupportedOperationException("method not suported for this implementation");
	}

	@Override
	public TransportProtocolBuilder addResponseDataProtocol(DataProtocolBuilder dphBuilder) {
		throw new UnsupportedOperationException("method not suported for this implementation");
	}
}
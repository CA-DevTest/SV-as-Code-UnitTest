/**
 * 
 */
package com.ca.devtest.sv.devtools.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gaspa03
 *
 */
public class TransportProtocolDefinitionImpl implements TransportProtocolDefinition{

	private boolean allAreStateless = true;
	private boolean asObject = true;
	private boolean desensitize = false;
	private boolean duptxns = true;

	private List<DataProtocolDefinition> requestSide = new ArrayList<DataProtocolDefinition>();
	private List<DataProtocolDefinition> responseSide = new ArrayList<DataProtocolDefinition>();
	private String TRANSPORT_TPL = "<Transport allAreStateless=\"%b\" asObject=\"%b\" desensitize=\"%b\" duptxns=\"%b\"><RequestSide>%s</RequestSide><ResponseSide>%s</ResponseSide></Transport>";
	private final String type;
	private final HashMap<String, String> parameters = new HashMap<String, String>();

	public TransportProtocolDefinitionImpl(String type) {
		super();
		this.type = type;
	}

	/**
	 * @return the allAreStateless
	 */
	public final boolean isAllAreStateless() {
		return allAreStateless;
	}

	/**
	 * @param allAreStateless
	 *            the allAreStateless to set
	 */
	public final void setAllAreStateless(boolean allAreStateless) {
		this.allAreStateless = allAreStateless;
	}

	/**
	 * @return the asObject
	 */
	public final boolean isAsObject() {
		return asObject;
	}

	/**
	 * @param asObject
	 *            the asObject to set
	 */
	public final void setAsObject(boolean asObject) {
		this.asObject = asObject;
	}

	/**
	 * @return the desensitize
	 */
	public final boolean isDesensitize() {
		return desensitize;
	}

	/**
	 * @param desensitize
	 *            the desensitize to set
	 */
	public final void setDesensitize(boolean desensitize) {
		this.desensitize = desensitize;
	}

	/**
	 * @return the duptxns
	 */
	public final boolean isDuptxns() {
		return duptxns;
	}

	/**
	 * @param duptxns
	 *            the duptxns to set
	 */
	public final void setDuptxns(boolean duptxns) {
		this.duptxns = duptxns;
	}

	/**
	 * @param requestSide
	 *            the requestSide to set
	 */
	public final void setRequestSide(List<DataProtocolDefinition> requestSide) {
		this.requestSide = requestSide;
	}

	/**
	 * @param responseSide
	 *            the responseSide to set
	 */
	public final void setResponseSide(List<DataProtocolDefinition> responseSide) {
		this.responseSide = responseSide;
	}

	/**
	 * @return the requestSide
	 */
	public final List<DataProtocolDefinition> getRequestSide() {
		return requestSide;
	}

	/**
	 * @return the responseSide
	 */
	public final List<DataProtocolDefinition> getResponseSide() {
		return responseSide;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ca.devtest.sv.devtools.tph.BaseProtocol#doPrintSpecific()
	 */
	protected String doPrintSpecific() {
		String requestSide = printRequestSide();
		String responsetSide = printResponseSide();
		return String.format(TRANSPORT_TPL, allAreStateless, asObject, desensitize, duptxns, requestSide,
				responsetSide);

	}

	/**
	 * @return
	 */
	private String printRequestSide() {
		StringBuilder definition = new StringBuilder();
		if (!requestSide.isEmpty()) {
			for (DataProtocolDefinition dataProtocolDefinition : requestSide) {
				dataProtocolDefinition.printXml(definition);
			}
		}

		return definition.toString();
	}

	/**
	 * @return
	 */
	private String printResponseSide() {
		StringBuilder definition = new StringBuilder();
		if (!responseSide.isEmpty()) {
			for (DataProtocolDefinition dataProtocolDefinition : responseSide) {
				dataProtocolDefinition.printXml(definition);
			}
		}
		return definition.toString();
	}

	/**
	 * @return
	 */
	public String toVrsContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<RecordingSession nonLeaf=\"WIDE\" leaf=\"LOOSE\" asObject=\"true\">");
		printXml(sb);
		sb.append("</RecordingSession>");
		return sb.toString();

	}

	/**
	 * @return
	 */
	protected final String printParameters() {
		StringBuilder result = new StringBuilder();

		if (!parameters.isEmpty()) {
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				result.append("<").append(key).append(">").append(parameters.get(key)).append("</").append(key)
						.append(">");
			}
		}
		return result.toString();
	}

	/**
	 * @param definition
	 */
	protected void printXml(StringBuilder definition) {
		definition.append("<Protocol type=\"").append(type).append("\">").append(printParameters())
				.append(doPrintSpecific()).append("</Protocol>");

	}

	@Override
	public Map<String, String> getParameters() {
		return parameters;
	}

}
package com.ca.devtest.sv.devtools.annotation;

public enum VirtualServiceType {
	// Objets directement construits
	RRPAIRS("RRPairs", "http://%s:%s/api/Dcm/VSEs/%s/actions/createService"), VSM("MARFile",
			"http://%s:%s/api/Dcm/VSEs/%s/actions/deployMar");

	private String name = "";
	private String urlPattern = "";

	// Constructeur
	VirtualServiceType(String name, String url) {
		this.name = name;
		this.urlPattern = url;
	}

	/**
	 * @return url pattern
	 */
	public String geturlPattern(){
		return urlPattern;
	}
	public String toString() {
		return name;
	}

}

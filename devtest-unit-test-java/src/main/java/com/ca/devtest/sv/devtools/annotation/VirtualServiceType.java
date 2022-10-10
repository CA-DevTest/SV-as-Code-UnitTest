package com.ca.devtest.sv.devtools.annotation;

public enum VirtualServiceType {
	// Objets directement construits
	RRPAIRS(Constants.DCM_API_RRPAIRS, Constants.DCM_API_RRPAIRS_URL),
	VSM(Constants.DCM_API_VSM, Constants.DCM_API_VSM_URL);

	private String type = "";
	private String urlPattern = "";

	// Constructeur
	VirtualServiceType(String type, String url) {
		this.type = type;
		this.urlPattern = url;
	}

	/**
	 * @return url pattern
	 */
	public String geturlPattern(){
		return urlPattern;
	}
	public String getType() { return type;}

    public String toString() {
		return type;
	}

}

package com.ca.devtest.sv.devtools.annotation.v3;

import com.ca.devtest.sv.devtools.annotation.Constants;

/**
 * @author sm632260
 *
 */
public enum VirtualServiceV3Type {
    CREATE(Constants.V3_API_CREATEVS, Constants.V3_API_CREATEVS_URL),
    UPDATE(Constants.V3_API_UPDATEVS, Constants.V3_API_UPDATEVS_URL);

    private final String type;
    private final String urlPattern;

    VirtualServiceV3Type(String type, String urlPattern){
        this.type=type;
        this.urlPattern=urlPattern;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public String getType() { return type; }
}

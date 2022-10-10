package com.ca.devtest.sv.devtools.services;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;

/**
 * @author sm632260
 *
 */
public interface VirtualServiceInterface {
    VirtualServiceEnvironment getVse();
    String getType();
    void setType(String type);
    String getUrl();
    void setUrl(String url);
    String getName();
    String getGroup();
    void deploy() throws Exception;
    void unDeploy() throws Exception;
    void exists() throws Exception;
    String getDeployedName();
    void setDeployedName(String deployedName);
    void clean();
}

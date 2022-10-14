package com.ca.devtest.sv.devtools.services;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author sm632260
 *
 */
public abstract class AbstractVirtualService implements VirtualServiceInterface{
    VirtualServiceEnvironment vse;
    String url;
    String type;
    String name;
    String deployedName;
    public AbstractVirtualService(String name, String type, String url, VirtualServiceEnvironment vse){
        if (name == null)
            throw new IllegalArgumentException("Service Name cannot be null");
        this.name = name;
        this.vse = vse;
        this.type = type;
        this.url = url;
    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public void setType(String type){
        this.type=type;
    }

    @Override
    public String getUrl(){
        return url;
    }

    @Override
    public void setUrl(String url){
        this.url = url;
    }

    @Override
    public VirtualServiceEnvironment getVse() {
        return this.vse;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getGroup() {
        return vse.getGroup();
    }

    @Override
    public void deploy() throws IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException {
        getVse().deployService(this);
    }

    @Override
    public void unDeploy() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        getVse().unDeployService(this);
    }

    @Override
    public void exists() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        getVse().exist(this);
    }

    @Override
    public String getDeployedName() {
        return deployedName;
    }

    @Override
    public void setDeployedName(String deployedName) {
        this.deployedName = deployedName;
    }
}

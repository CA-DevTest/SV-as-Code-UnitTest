package com.ca.devtest.sv.devtools.services.v3;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author sm632260
 *
 */
class Endpoint {
    boolean useSSL;
    String host;
    String port;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    SSLConfig sslConfig;

    private Endpoint() {
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public SSLConfig getSslConfig() {
        return sslConfig;
    }

    public void setSslConfig(SSLConfig sslConfig) {
        this.sslConfig = sslConfig;
    }

    public static class EndpointBuilder{

        private final Endpoint endpointInstance = new Endpoint();
        private EndpointBuilder(){
        }

        public static EndpointBuilder builder(){
            return new EndpointBuilder();
        }

        public EndpointBuilder withUseSSL(boolean useSSL){
            endpointInstance.setUseSSL(useSSL);
            return  this;
        }

        public EndpointBuilder withHost(String host){
            endpointInstance.setHost(host);
            return this;
        }

        public EndpointBuilder withPort(String port){
            endpointInstance.setPort(port);
            return this;
        }

        public EndpointBuilder withSSLConfig(SSLConfig sslConfig){
            endpointInstance.setSslConfig(sslConfig);
            return this;
        }

        public Endpoint build(){
            //to avoid generating empty SSL info when it is not configured
            if(endpointInstance.getSslConfig().getAlias().isEmpty() &&
                    endpointInstance.getSslConfig().getAliasPassword().isEmpty() &&
                    endpointInstance.getSslConfig().getKeystoreFile().isEmpty() &&
                    endpointInstance.getSslConfig().getKeystorePassword().isEmpty()){
                endpointInstance.setSslConfig(null);
            }
            return endpointInstance;
        }
    }
}

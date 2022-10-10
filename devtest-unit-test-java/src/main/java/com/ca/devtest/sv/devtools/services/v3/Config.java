package com.ca.devtest.sv.devtools.services.v3;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author sm632260
 *
 */
class Config {
    VirtualService virtualService;
    TransportProtocol transportProtocol;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    DataProtocol dataProtocol;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    DataProtocol[] dataProtocols;

    private Config(){
    }

    public VirtualService getVirtualService() {
        return virtualService;
    }

    public void setVirtualService(VirtualService virtualService) {
        this.virtualService = virtualService;
    }

    public TransportProtocol getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(TransportProtocol transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public DataProtocol getDataProtocol() {
        return dataProtocol;
    }

    public void setDataProtocol(DataProtocol dataProtocol) {
        this.dataProtocol = dataProtocol;
    }

    public DataProtocol[] getDataProtocols() {
        return dataProtocols;
    }

    public void setDataProtocols(DataProtocol[] dataProtocols) {
        this.dataProtocols = dataProtocols;
    }

    public static class ConfigBuilder {

        private final Config configInstance = new Config();
        private ConfigBuilder(){}

        public static ConfigBuilder builder(){
            return new ConfigBuilder();
        }

        public ConfigBuilder withVirtualService(VirtualService virtualService){
            configInstance.setVirtualService(virtualService);
            return this;
        }

        public ConfigBuilder withTransportProtocol(TransportProtocol transportProtocol){
            configInstance.setTransportProtocol(transportProtocol);
            return this;
        }

        public ConfigBuilder withDataProtocols(DataProtocol[] dataProtocols) {
            if (dataProtocols != null && dataProtocols.length == 1) {
                configInstance.setDataProtocol(dataProtocols[0]);
            } else {
                configInstance.setDataProtocols(dataProtocols);
            }
            return this;
        }

        public Config build(){
            return configInstance;
        }
    }
}

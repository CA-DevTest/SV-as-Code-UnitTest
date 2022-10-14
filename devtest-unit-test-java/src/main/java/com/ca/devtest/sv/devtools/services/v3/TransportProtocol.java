package com.ca.devtest.sv.devtools.services.v3;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author sm632260
 *
 */
public class TransportProtocol {
    String typeId;
    String basePath;
    boolean useGateway;
    boolean hostHeaderPassThrough;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Endpoint targetEndpoint;
    Endpoint recordingEndpoint;

    public TransportProtocol() {
        this.typeId="HTTP";
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public boolean isUseGateway() {
        return useGateway;
    }

    public void setUseGateway(boolean useGateway) {
        this.useGateway = useGateway;
    }

    public boolean isHostHeaderPassThrough() {
        return hostHeaderPassThrough;
    }

    public void setHostHeaderPassThrough(boolean hostHeaderPassThrough) {
        this.hostHeaderPassThrough = hostHeaderPassThrough;
    }

    public Endpoint getTargetEndpoint() {
        return targetEndpoint;
    }

    public void setTargetEndpoint(Endpoint targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public Endpoint getRecordingEndpoint() {
        return recordingEndpoint;
    }

    public void setRecordingEndpoint(Endpoint recordingEndpoint) {
        this.recordingEndpoint = recordingEndpoint;
    }

    public static class TransportProtocolBuilder{

        private final TransportProtocol transportProtocolInstance = new TransportProtocol();
        private TransportProtocolBuilder(){}

        public static TransportProtocolBuilder builder(){
            return new TransportProtocolBuilder();
        }

        public TransportProtocolBuilder withTypeId(String typeId){
            transportProtocolInstance.setTypeId(typeId);
            return this;
        }

        public TransportProtocolBuilder withBasePath(String basePath){
            transportProtocolInstance.setBasePath(basePath);
            return this;
        }

        public TransportProtocolBuilder withUseGateway(boolean useGateway){
            transportProtocolInstance.setUseGateway(useGateway);
            return this;
        }

        public TransportProtocolBuilder withHostHeaderPassThrough(boolean hostHeaderPassThrough){
            transportProtocolInstance.setHostHeaderPassThrough(hostHeaderPassThrough);
            return this;
        }

        public TransportProtocolBuilder withTargetEndpoint(Endpoint endpoint){
            transportProtocolInstance.setTargetEndpoint(endpoint);
            return this;
        }

        public TransportProtocolBuilder withRecordingEndpoint(Endpoint endpoint){
            transportProtocolInstance.setRecordingEndpoint(endpoint);
            return this;
        }

        public TransportProtocol build(){
            //to avoid generating Recording Endpoint json format when it is not configured
            if(transportProtocolInstance!=null &&
                    transportProtocolInstance.getTargetEndpoint()!=null &&
                    !transportProtocolInstance.getTargetEndpoint().isUseSSL()
                    && transportProtocolInstance.getTargetEndpoint().getHost().isEmpty()
                    && transportProtocolInstance.getTargetEndpoint().getPort().isEmpty()){
                transportProtocolInstance.setTargetEndpoint(null);
            }
            return transportProtocolInstance;
        }
    }
}

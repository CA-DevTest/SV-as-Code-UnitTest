package com.ca.devtest.sv.devtools.services.v3;

/**
 * @author sm632260
 *
 */
class DataProtocol {
    String typeId;
    boolean forRequest;

    public DataProtocol() {
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public boolean getForRequest() {
        return forRequest;
    }

    public void setForRequest(boolean forRequest) {
        this.forRequest = forRequest;
    }

    public static class DataProtocolBuilder{
        private final DataProtocol dataProtocolInstance = new DataProtocol();

        private DataProtocolBuilder(){}

        public static DataProtocolBuilder builder (){
            return new DataProtocolBuilder();
        }

        public DataProtocolBuilder withTypeId(String typeId){
            dataProtocolInstance.setTypeId(typeId);
            return this;
        }

        public DataProtocolBuilder withForRequest(boolean forRequest){
            dataProtocolInstance.setForRequest(forRequest);
            return this;
        }

        public DataProtocol build(){
            return dataProtocolInstance;
        }
    }
}

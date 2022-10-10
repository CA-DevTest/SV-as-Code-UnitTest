package com.ca.devtest.sv.devtools.services.v3;

import com.ca.devtest.sv.devtools.annotation.v3.*;
import com.ca.devtest.sv.devtools.utils.ObjectMapperUtil;


/**
 * @author sm632260
 *
 */
public class ConfigObjectBuilder {

    public static String buildConfigJSON(DevTestVirtualServiceV3 virtualService, String deployedName) throws Exception{
        if(deployedName != null) {
            return ObjectMapperUtil.objectToJSON(buildCreateConfig(virtualService, deployedName));
        }else{
            //currently update config of V3 update API have does not have many parameters hence
            //return string instead of using java objects and converting it.
            if(virtualService.overwriteTxns()) {
                return "{\"virtualService\":{},\"transportProtocol\":{\"overwriteTxns\":true}}";
            }else{
                return "{\"virtualService\":{},\"transportProtocol\":{\"overwriteTxns\":false}}";
            }
        }
    }

    private static Config buildCreateConfig(DevTestVirtualServiceV3 virtualService, String deployedName){

        VirtualService virtualServiceConfig = VirtualService.VirtualServiceBuilder.builder()
                .withName(deployedName)
                .withDescription(virtualService.description())
                .withVerison(virtualService.version())
                .withCapacity(virtualService.capacity())
                .withAutoRestart(virtualService.autoRestartEnabled())
                .withGroupTag(virtualService.groupTag())
                .withStartOnDeploy(virtualService.startOnDeployEnabled())
                .withStatus(virtualService.status())
                .withThinkScale(virtualService.thinkScale())
                .build();

        TransportProtocol transportProtocolConfig = TransportProtocol.TransportProtocolBuilder.builder()
                .withBasePath(virtualService.basePath())
                .withHostHeaderPassThrough(virtualService.transportProtocolConfig().hostHeaderPassThrough())
                .withUseGateway(virtualService.transportProtocolConfig().useGateway())
                .withTypeId(virtualService.transportProtocolConfig().typeId())
                .withRecordingEndpoint(buildRecordingEndpoint(virtualService))
                .withTargetEndpoint(buildTargetEndpoint(virtualService.transportProtocolConfig().targetEndpoint()))
                .build();

        return Config.ConfigBuilder.builder()
                .withVirtualService(virtualServiceConfig)
                .withDataProtocols(buildDatprotocols(virtualService.dataProtocolsConfig()))
                .withTransportProtocol(transportProtocolConfig)
                .build();
    }

    private static DataProtocol[] buildDatprotocols(DataProtocolConfig[] dataProtocolsConfig){
        DataProtocol[] dataProtocols = new DataProtocol[dataProtocolsConfig.length];
        for(int index=0;index<dataProtocolsConfig.length;index++){
            dataProtocols[index] = buildDataProtocol(dataProtocolsConfig[index]);
        }
        return dataProtocols;
    }

    private static DataProtocol buildDataProtocol(DataProtocolConfig dataProtocolConfig){
        return DataProtocol.DataProtocolBuilder.builder()
                .withTypeId(dataProtocolConfig.typeId())
                .withForRequest(dataProtocolConfig.forRequest())
                .build();
    }

    private static Endpoint buildTargetEndpoint(TargetEndpointConfig endpointConfig){
        return Endpoint.EndpointBuilder.builder()
                .withHost(endpointConfig.host())
                .withPort(endpointConfig.port())
                .withUseSSL(endpointConfig.useSSL())
                .withSSLConfig(
                        SSLConfig.SSLConfigBuilder.builder()
                                .withAlias(endpointConfig.sslConfig().alias())
                                .withAliasPassword(endpointConfig.sslConfig().aliasPassword())
                                .withKeystoreFile(endpointConfig.sslConfig().keystoreFile())
                                .withKeystorePassword(endpointConfig.sslConfig().keystorePassword())
                                .build()
                )
                .build();
    }

    private static Endpoint buildRecordingEndpoint(DevTestVirtualServiceV3 virtualService){
        RecordingEndpointConfig endpointConfig = virtualService.transportProtocolConfig().recordingEndpoint();
        return Endpoint.EndpointBuilder.builder()
                .withHost(endpointConfig.host())
                .withPort(virtualService.port())
                .withUseSSL(endpointConfig.useSSL())
                .withSSLConfig(
                        SSLConfig.SSLConfigBuilder.builder()
                                .withAlias(endpointConfig.sslConfig().alias())
                                .withAliasPassword(endpointConfig.sslConfig().aliasPassword())
                                .withKeystoreFile(endpointConfig.sslConfig().keystoreFile())
                                .withKeystorePassword(endpointConfig.sslConfig().keystorePassword())
                                .build()
                )
                .build();
    }
}

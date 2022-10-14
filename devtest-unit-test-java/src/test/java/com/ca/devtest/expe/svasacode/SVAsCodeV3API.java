package com.ca.devtest.expe.svasacode;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolFromVrsBuilder;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;
import com.ca.devtest.sv.devtools.services.builder.v3.VirtualServiceV3Builder;
import com.ca.devtest.sv.devtools.services.v3.*;
import org.junit.Test;

import java.io.File;

public class SVAsCodeV3API {
    @Test
    public void testAPI() throws Exception {

        File rrpairsFolder = new File(
                (SvAsCodeAPI.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "v3"
                        + File.separatorChar + "rrpair").replaceAll("%20", " "));

        // Create
        DevTestClient devtest =
                new DevTestClient("http", "localhost", "VSE", "svpower", "svpower", "demo", "", "");

        // Virtual Service builder
        VirtualServiceV3Builder v3VSBuilder = devtest.withV3API("myservice", rrpairsFolder);
        v3VSBuilder.setInputFile1("operation-9-req.txt");
        v3VSBuilder.setInputFile2("operation-9-rsp.txt");

        // build Transport Protocol
        Config configObject = Config.ConfigBuilder.builder()
                .withVirtualService( VirtualService.VirtualServiceBuilder.builder()
                        .withName(v3VSBuilder.getDeployedName())
                        .build())
                .withTransportProtocol(
                        TransportProtocol.TransportProtocolBuilder.builder()
                                .withBasePath("/")
                                .withRecordingEndpoint(
                                        Endpoint.EndpointBuilder.builder()
                                                .withPort("8081")
                                                .withSSLConfig(
                                                        SSLConfig.SSLConfigBuilder.builder().build()
                                                )
                                                .build()
                                ).build()

                ).build();

        v3VSBuilder.setConfig(ConfigObjectBuilder.convertToJson(configObject));
        VirtualServiceInterface sv = v3VSBuilder.build();
        // Deploy VS
        sv.deploy();
        // unDeploy VS
        sv.unDeploy();
    }

    @Test
    public void testAPI2() throws Exception {

        File rrpairsFolder = new File(
                (SvAsCodeAPI.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "v3"
                        + File.separatorChar + "rrpair").replaceAll("%20", " "));

        // Create
        DevTestClient devtest =
                new DevTestClient("http", "localhost", "VSE", "svpower", "svpower", "demo2", "", "");

        // Virtual Service builder
        VirtualServiceV3Builder v3VSBuilder = devtest.withV3API("myservice", rrpairsFolder);
        v3VSBuilder.setInputFile1("operation-9-req.txt");
        v3VSBuilder.setInputFile2("operation-9-rsp.txt");
        v3VSBuilder.setConfig("{\"virtualService\":{\"version\":\"1.0\",\"name\":\""+v3VSBuilder.getDeployedName()+"\",\"description\":\"Deployed using SV-as-Code\",\"status\":\"\",\"capacity\":0,\"thinkScale\":0,\"autoRestart\":true,\"startOnDeploy\":true,\"groupTag\":\"\"},\"transportProtocol\":{\"typeId\":\"HTTP\",\"basePath\":\"/\",\"useGateway\":false,\"hostHeaderPassThrough\":false,\"recordingEndpoint\":{\"useSSL\":false,\"host\":\"\",\"port\":\"8081\"}}}");
        VirtualServiceInterface sv = v3VSBuilder.build();
        // Deploy VS
        sv.deploy();
        // unDeploy VS
        sv.unDeploy();
    }
}

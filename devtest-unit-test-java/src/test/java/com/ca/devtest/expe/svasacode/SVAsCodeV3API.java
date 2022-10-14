package com.ca.devtest.expe.svasacode;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolFromVrsBuilder;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;
import com.ca.devtest.sv.devtools.services.builder.v3.VirtualServiceV3Builder;
import com.ca.devtest.sv.devtools.services.v3.*;
import com.ca.devtest.sv.devtools.v3.HttpUtils;
import com.ca.devtest.sv.devtools.v3.ResponseParser;
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
        v3VSBuilder.setInputFile1("operation-8-req.txt");
        v3VSBuilder.setInputFile2("operation-8-rsp.txt");

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
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http","localhost",
                "8081", "import/test/operation-8");
        ResponseParser vsResponseParser = HttpUtils.GET_VS_DETAILS("http","localhost", "1505", "VSE", v3VSBuilder.getDeployedName());
        //ResponseParser vsSpecificParser = HttpUtils.GET_VS_SPECIFICS(API_PROTOCOL, "localhost", "1505", "VSE", "V3Test.vsV3_Deploy");
        assert (responseParser != null);
        assert (responseParser.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (vsResponseParser != null);
        assert (vsResponseParser.getValue("$.modelName").equals(v3VSBuilder.getDeployedName()));
        assert (vsResponseParser.getValue("$.capacity").equals("1"));
        assert (vsResponseParser.getValue("$.thinkScale").equals("0"));
        assert (vsResponseParser.getValue("$.autoRestartEnabled").equals("true"));
        assert (vsResponseParser.getValue("$.executionMode").equals("Most Efficient"));
        assert (vsResponseParser.getValue("$.executionModeValue").equals("EFFICIENT"));
        assert (vsResponseParser.getValue("$.resourceName").equals("8081 : http :  : /"));
        assert (vsResponseParser.getValue("$.groupTag").isEmpty());
        assert (vsResponseParser.getValue("$.statusDescription").equals("running"));
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
        v3VSBuilder.setInputFile1("operation-8-req.txt");
        v3VSBuilder.setInputFile2("operation-8-rsp.txt");
        v3VSBuilder.setConfig("{\"virtualService\":{\"version\":\"1.0\",\"name\":\""+v3VSBuilder.getDeployedName()+"\",\"description\":\"Deployed using SV-as-Code\",\"status\":\"\",\"capacity\":0,\"thinkScale\":0,\"autoRestart\":true,\"startOnDeploy\":true,\"groupTag\":\"\"},\"transportProtocol\":{\"typeId\":\"HTTP\",\"basePath\":\"/\",\"useGateway\":false,\"hostHeaderPassThrough\":false,\"recordingEndpoint\":{\"useSSL\":false,\"host\":\"\",\"port\":\"8081\"}}}");
        VirtualServiceInterface sv = v3VSBuilder.build();
        // Deploy VS
        sv.deploy();
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http","localhost",
                "8081", "import/test/operation-8");
        ResponseParser vsResponseParser = HttpUtils.GET_VS_DETAILS("http","localhost", "1505", "VSE", v3VSBuilder.getDeployedName());
        //ResponseParser vsSpecificParser = HttpUtils.GET_VS_SPECIFICS(API_PROTOCOL, "localhost", "1505", "VSE", "V3Test.vsV3_Deploy");
        assert (responseParser != null);
        assert (responseParser.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (vsResponseParser != null);
        assert (vsResponseParser.getValue("$.modelName").equals(v3VSBuilder.getDeployedName()));
        assert (vsResponseParser.getValue("$.capacity").equals("1"));
        assert (vsResponseParser.getValue("$.thinkScale").equals("0"));
        assert (vsResponseParser.getValue("$.autoRestartEnabled").equals("true"));
        assert (vsResponseParser.getValue("$.executionMode").equals("Most Efficient"));
        assert (vsResponseParser.getValue("$.executionModeValue").equals("EFFICIENT"));
        assert (vsResponseParser.getValue("$.resourceName").equals("8081 : http :  : /"));
        assert (vsResponseParser.getValue("$.groupTag").isEmpty());
        assert (vsResponseParser.getValue("$.statusDescription").equals("running"));
        // unDeploy VS
        sv.unDeploy();
    }
}

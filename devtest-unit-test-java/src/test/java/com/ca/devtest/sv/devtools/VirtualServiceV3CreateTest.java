package com.ca.devtest.sv.devtools;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.v3.*;
import com.ca.devtest.sv.devtools.application.SoapClient;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;
import com.ca.devtest.sv.devtools.v3.HttpUtils;
import com.ca.devtest.sv.devtools.v3.ResponseParser;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * @author sm632260
 *
 */
@DevTestVirtualServer(deployServiceToVse = "VSE",groupName="V3Test")
public class VirtualServiceV3CreateTest {

    @Rule
    public VirtualServicesRule rules = new VirtualServicesRule();
    String API_PROTOCOL = "http";
    /**
     * Create and deploy VS with RRPair and with default configuration
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_Deploy",
            port = "24778",
            workingFolder = "v3/rrpair",
            inputFile2 = "operation-8-req.txt",
            inputFile1 = "operation-8-rsp.txt"
    )
    @Test
    public void vsV3_Deploy() throws InterruptedException {

        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http","localhost",
                "24778", "import/test/operation-8");
        ResponseParser vsResponseParser = HttpUtils.GET_VS_DETAILS(API_PROTOCOL,"localhost", "1505", "VSE", "V3Test.vsV3_Deploy");
        //ResponseParser vsSpecificParser = HttpUtils.GET_VS_SPECIFICS(API_PROTOCOL, "localhost", "1505", "VSE", "V3Test.vsV3_Deploy");
        assert (responseParser != null);
        assert (responseParser.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (vsResponseParser != null);
        assert (vsResponseParser.getValue("$.modelName").equals("V3Test.vsV3_Deploy"));
        assert (vsResponseParser.getValue("$.capacity").equals("1"));
        assert (vsResponseParser.getValue("$.thinkScale").equals("100"));
        assert (vsResponseParser.getValue("$.autoRestartEnabled").equals("true"));
        assert (vsResponseParser.getValue("$.executionMode").equals("Most Efficient"));
        assert (vsResponseParser.getValue("$.executionModeValue").equals("EFFICIENT"));
        assert (vsResponseParser.getValue("$.resourceName").equals("24778 : http :  : /"));
        assert (vsResponseParser.getValue("$.groupTag").isEmpty());
        assert (vsResponseParser.getValue("$.statusDescription").equals("running"));
        //assert (vsSpecificParser != null);
        //assert (vsSpecificParser.getValue("$.description").equals("Deployed using SV-as-Code"));
        //assert (vsSpecificParser.getValue("$.deployedBy").equals("svpower"));
    }

    /***
     * Create and deploy VS with RR pair and with custom configuration
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_RRPairCustomConfig",
            port = "24779",
            workingFolder = "v3/rrpair",
            inputFile2 = "operation-8-req.txt",
            inputFile1 = "operation-8-rsp.txt",
            groupTag = "CustomConfig",
            thinkScale = 200,
            autoRestartEnabled = false

    )
    @Test
    public void vsV3_RRPairCustomConfig() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24779", "import/test/operation-8");
        ResponseParser vsResponseParser = HttpUtils.GET_VS_DETAILS(API_PROTOCOL,"localhost", "1505", "VSE", "V3Test.vsV3_RRPairCustomConfig");
        assert (responseParser != null);
        assert (responseParser.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (vsResponseParser != null);
        assert (vsResponseParser.getValue("$.modelName").equals("V3Test.vsV3_RRPairCustomConfig"));
        assert (vsResponseParser.getValue("$.capacity").equals("1"));
        assert (vsResponseParser.getValue("$.thinkScale").equals("200"));
        assert (vsResponseParser.getValue("$.autoRestartEnabled").equals("false"));
        assert (vsResponseParser.getValue("$.executionMode").equals("Most Efficient"));
        assert (vsResponseParser.getValue("$.executionModeValue").equals("EFFICIENT"));
        assert (vsResponseParser.getValue("$.configurationName").equals("project.config"));
        assert (vsResponseParser.getValue("$.resourceName").equals("24779 : http :  : /"));
        assert (vsResponseParser.getValue("$.groupTag").equals("CustomConfig"));
        assert (vsResponseParser.getValue("$.status").equals("2"));
        assert (vsResponseParser.getValue("$.statusDescription").equals("running"));
        assert (vsResponseParser.getValue("$.errorCount").equals("0"));
    }

    /**
     * Create and Deploy VS with RRpair zip file
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_RRPairZip",
            port = "24778",
            workingFolder = "v3/rrpair",
            inputFile2 = "Op8andOp9-RRPairs.zip"
    )
    @Test
    public void vsV3_RRPairZip() {
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "import/test/operation-8");
        ResponseParser op9Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "import/test/operation-9");
        assert (op8Response != null);
        assert (op8Response.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (op9Response != null);
        assert (op9Response.getValue("$.TCEntry[0].termsType").equals("Operation 9 terms"));
    }

    /***
     * Create and deploy VS with VSM and VSI files
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_VSMVSI",
            port = "13712",
            workingFolder = "v3/vsmvsi",
            inputFile1 = "Op8AndOp9.vsi",
            inputFile2 = "Op8AndOp9.vsm"
    )
    @Test
    public void vsV3_VSMVSI() {
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "13712", "import/test/operation-8");
        ResponseParser op9Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "13712", "import/test/operation-9");
        assert (op8Response != null);
        assert (op8Response.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (op9Response != null);
        assert (op9Response.getValue("$.TCEntry[0].termsType").equals("Operation 9 terms"));
    }

    /***
     * Create and deploy VS with VSM and VSI files and custom port
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_VSMVSI_CustomPort",
            description = "Test Case vsV3_VSMVSI_CustomPort",
            port = "24000",
            workingFolder = "v3/vsmvsi_port_parameter",
            inputFile1 = "Op8AndOp9.vsi",
            inputFile2 = "Op8AndOp9.vsm"
    )
    @Test
    public void vsV3_VSMVSI_CustomPort() {
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24000", "import/test/operation-8");
        ResponseParser op9Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24000", "import/test/operation-9");
        assert (op8Response != null);
        assert (op8Response.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (op9Response != null);
        assert (op9Response.getValue("$.TCEntry[0].termsType").equals("Operation 9 terms"));
    }

    /***
     * Create and Deploy VS with swagger file
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_SwaggerFile",
            port = "24778",
            workingFolder = "v3/swagger",
            inputFile1 = "swagger.json"
    )
    @Test
    public void vsV3_SwaggerFile() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "v2/store/inventory");
        String value = responseParser.getValue("//root/integer_0");
        assert (responseParser != null);
        assert (value.equals("1"));
    }

    /***
     * Create and deploy VS with Swagger url
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_SwaggerUrl",
            port = "24778",
            workingFolder = "v3/swagger",
            swaggerurl = "https://petstore.swagger.io/v2/swagger.json"
    )
    @Test
    public void vsV3_SwaggerUrl() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "v2/store/inventory");
        String value = responseParser.getValue("//root/integer_0");
        String str = (new File("v3/swager", "swagger.json")).toURI().toString();
        assert (responseParser != null);
        assert (value.equals("1"));
    }

    /***
     * Create and deploy VS with raml file
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_RAML",
            port = "24778",
            workingFolder = "v3/raml",
            inputFile1 = "storage.raml"
    )
    @Test
    public void vsV3_RAML() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "organizations/S5RnX22D");
        String value = responseParser.getValue("$.name");
        assert (responseParser != null);
        assert (value.equals("name: Acme"));
    }

    /***
     * Create and deploy VS with raml file
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_RAMLUrl",
            port = "24778",
            workingFolder = "v3/raml",
            ramlurl = "file:///Users/sachinmaske/Code/SV-as-Code/devtest-unit-test-java/src/test/resources/v3/raml/storage.raml"
    )
    @Test
    public void vsV3_RAMLUrl() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "organizations/S5RnX22D");
        String value = responseParser.getValue("$.name");
        assert (responseParser != null);
        assert (value.equals("name: Acme"));
    }
    /**
     * Create and deploy VS with wadl file
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_WADLFile",
            port = "24778",
            workingFolder = "v3/wadl",
            inputFile1 = "os-services.wadl"
    )
    @Test
    public void vsV3_WADLFile() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "/v2/tenant_id/os-services");
        String value = responseParser.getValue("$.Lang");
        assert (responseParser != null);
        assert (value != null && !value.isEmpty());
    }

    /**
     * Create and deploy VS with wadl url
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_WADLUrl",
            port = "24778",
            workingFolder = "v3/wadl",
            wadlurl = "http://rackerlabs.github.io/wadl2swagger/openstack/wadls/os-services.wadl"
    )
    @Test
    public void vsV3_WADLUrl() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "/v2/tenant_id/os-services");
        String value = responseParser.getValue("$.Lang");
        assert (responseParser != null);
        assert (value != null && !value.isEmpty());
    }

    /***
     * Create and Deploy VS with sidecars files
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_SideCars",
            port = "13712",
            workingFolder = "v3/sidecars",
            inputFile1 = "rrpair-sidecars.zip"
    )
    @Test
    public void vsV3_SideCars() throws InterruptedException {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "13712", "import/test/operation-8");
        //ResponseParser vsResponseParser = HttpUtils.GET_VS_SPECIFICS(API_PROTOCOL, "localhost", "1505", "VSE", "V3Test.vsV3_SideCars");
        assert (responseParser != null);
        //assert (vsResponseParser.getValue("$.statelessTransactions[0].defaultResponses[0].thinkTime").equals("101"));
    }

    /***
     * Create and Deploy VS with SOAP dph
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_RRPairSoap",
            port = "24778",
            workingFolder = "rrpairs/soap",
            inputFile2 = "getUser-req.xml",
            inputFile1 = "getUser-rsp.xml",
            dataProtocolsConfig = {
                @DataProtocolConfig(
                        typeId = "SOAPDPH",
                        forRequest = true
                )
            }
    )
    @Test
    public void vsV3_RRPairSoap() throws Exception {
        int port = 24778;
        String path = "/";
        SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
        URL url = getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml");
        File requestFile = new File(getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml").toURI());
        String request = FileUtils.readFileToString(requestFile, "UTF-8");
        String response = soapclient.callService(path, request);
        assert (response.contains("luther@itko.com"));
    }

    /***
     * Create and Deploy VS with SOAP Body and Header dph
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_RRPairMultipleDPH",
            port = "24778",
            workingFolder = "v3/soap_header_body",
            inputFile2 = "getMovieInformation-req.xml",
            inputFile1 = "getMovieInformation-rsp.xml",
            dataProtocolsConfig =  {
                @DataProtocolConfig(
                        typeId = "SOAPDPH",
                        forRequest = true
                ),
                @DataProtocolConfig(
                        typeId = "SOAPHEADERDPH",
                        forRequest = true
                )
            }

    )
    @Test
    public void vsV3_RRPairMultipleDPH() throws Exception {
        int port = 24778;
        String path = "/";
        SoapClient soapclient = new SoapClient("localhost", String.valueOf(port));
        URL url = getClass().getClassLoader().getResource("v3/soap_header_body/getMovieInformation-req.xml");
        File requestFile = new File(getClass().getClassLoader().getResource("v3/soap_header_body/getMovieInformation-req.xml").toURI());
        String request = FileUtils.readFileToString(requestFile, "UTF-8");
        String response = soapclient.callService(path, request);
        assert(response.contains("Movie Id"));
        assert(response.contains("Movie Name Goes Here"));
    }

    /**
     * Create and deploy VS with custom target and recording endpoint.
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_RRPairConfigObject",
            description = "Created with annotation provided for function vsV3_RRPairConfigObject",
            port = "24778",
            workingFolder = "v3/rrpair",
            inputFile2 = "operation-8-req.txt",
            inputFile1 = "operation-8-rsp.txt",
            transportProtocolConfig = @TransportProtocolConfig(
                    typeId = "HTTP",
                    useGateway = false,
                    hostHeaderPassThrough = true,
                    targetEndpoint = @TargetEndpointConfig(
                            host = "livehost",
                            port = "8080",
                            useSSL = true
                    ),
                    recordingEndpoint = @RecordingEndpointConfig(
                            host = "recordinghost",
                            useSSL = true
                    )
            )
    )
    @Test
    public void vsV3_RRPairConfigObject() {
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "import/test/operation-8");
        ResponseParser vsResponseParser = HttpUtils.GET_VS_DETAILS(API_PROTOCOL, "localhost", "1505", "VSE", "V3Test.vsV3_Deploy");
        assert (responseParser != null);
        assert (responseParser.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
    }

    /**
     * Creat and Deploy multiple VS
     */
    @DevTestVirtualServicesV3(
            value = {@DevTestVirtualServiceV3(
                    serviceName = "vsV3_RRPair2VS1",
                    port = "24778",
                    workingFolder = "v3/rrpair",
                    inputFile2 = "operation-8-req.txt",
                    inputFile1 = "operation-8-rsp.txt"
                    ),
                    @DevTestVirtualServiceV3(
                            port = "13712",
                            serviceName = "vsV3_RRPair2VS2",
                            workingFolder = "v3/rrpair",
                            inputFile2 = "operation-9-req.txt",
                            inputFile1 = "operation-9-rsp.txt"
                    )}
    )
    @Test
    public void vsV3_RRPair2VS() {
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "import/test/operation-8");
        ResponseParser op9Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "13712", "import/test/operation-9");
        assert (op8Response != null);
        assert (op8Response.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (op9Response != null);
        assert (op9Response.getValue("$.TCEntry[0].termsType").equals("Operation 9 terms"));
    }

    /***
     * Create VS with vsm, vsi, data and active config file
     */
    @DevTestVirtualServiceV3(
            serviceName = "vsV3_Data_Config_Create",
            port = "8001",
            workingFolder = "v3/activeconfig_data",
            inputFile2 = "DataDriven.vsi",
            inputFile1 = "DataDriven.vsm",
            dataFile = "Data.xlsx",
            activeConfig = "project.config"
    )
    @Test
    public void vsV3_Data_Config_Create() {
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "8001", "import/test/operation-8");
        assert (op8Response != null);
        assert (op8Response.getValue("$.Id").equals("1"));
        assert (op8Response.getValue("$.Movie").equals("Avatar"));
        assert (op8Response.getValue("$.LIVE_INVOCATION_SERVER").equals("localhost"));
        assert (op8Response.getValue("$.LIVE_INVOCATION_PORT").equals("8080"));
    }


   @DevTestVirtualServiceV3(
            serviceName = "vsV3_SSLConfig",
            description = "Created with annotation provided for function vsV3_RRPairConfigObject",
            port = "24778",
            workingFolder = "v3/rrpair",
            inputFile2 = "operation-8-req.txt",
            inputFile1 = "operation-8-rsp.txt",
            transportProtocolConfig = @TransportProtocolConfig(
                    typeId = "HTTP",
                    useGateway = false,
                    hostHeaderPassThrough = true,
                    recordingEndpoint = @RecordingEndpointConfig(
                            host = "recordinghost",
                            useSSL = true,
                            sslConfig = @SSLConfig (
                                    keystorePassword = "passphrase",
                                    keystoreFile = "/Applications/CA/DevTest/webreckeys.ks",
                                    aliasPassword = "passphrase",
                                    alias = "lisa"
                            )
                    )
            )
    )
    @Test
    public void vsV3_SSLConfig(){
       //Before executing this test case extract certificate from webreckeys.ks and then add it to jre with following commands

       //command to extract certificates
       //keytool -export -alias lisa -keystore /Applications/CA/DevTest/webreckeys.ks -rfc -file webreckeys.cert

       //command to import into jdk certificates
       //sudo keytool -import -alias svdevtest -trustcacerts -keystore ./jre/lib/security/cacerts -file ~/webreckeys.cert
       ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT,  "https", "localhost",
                "24778","import/test/operation-8");
       ResponseParser vsResponseParser = HttpUtils.GET_VS_DETAILS(API_PROTOCOL, "localhost", "1505", "VSE", "V3Test.vsV3_Deploy");
       assert (responseParser!=null);
       assert (responseParser.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
    }
}

package com.ca.devtest.sv.devtools;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServiceV3;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServicesV3;
import com.ca.devtest.sv.devtools.annotation.v3.VirtualServiceV3Type;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;
import com.ca.devtest.sv.devtools.v3.HttpUtils;
import com.ca.devtest.sv.devtools.v3.ResponseParser;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author sm632260
 *
 */
@DevTestVirtualServer(deployServiceToVse = "VSE",groupName="V3UpdateTest")
public class VirtualServiceV3UpdateTest {

    @Rule
    public VirtualServicesRule rules = new VirtualServicesRule();

    /**
     * Create, Update and Deploy VS with new RRPair
     */
    @DevTestVirtualServicesV3 (
        value = {@DevTestVirtualServiceV3(
                serviceName = "vsV3_Deploy",
                port = "24778",
                workingFolder = "v3/rrpair",
                inputFile2 = "operation-8-req.txt",
                inputFile1 = "operation-8-rsp.txt"
        ),
        @DevTestVirtualServiceV3(
                serviceName = "vsV3_Deploy",
                workingFolder = "v3/rrpair",
                inputFile2 = "operation-9-req.txt",
                inputFile1 = "operation-9-rsp.txt",
                type = VirtualServiceV3Type.UPDATE
        )}
    )
    @Test
    public void vsV3_Deploy(){
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778","import/test/operation-8");
        ResponseParser op9Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778", "import/test/operation-9");
        assert (op8Response!=null);
        assert (op8Response.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
        assert (op9Response != null);
        assert (op9Response.getValue("$.TCEntry[0].termsType").equals("Operation 9 terms"));
    }

    /**
     * Create, Update and Deploy VS with new Data and active config file.
     */
    @DevTestVirtualServicesV3 (
        value = {
            @DevTestVirtualServiceV3(
                serviceName = "vsV3_Data_Config_Update",
                port = "8001",
                workingFolder = "v3/activeconfig_data",
                inputFile2 = "DataDriven.vsi",
                inputFile1 = "DataDriven.vsm",
                dataFile = "Data.xlsx",
                activeConfig = "project.config",
                parameters = {
                        @Parameter(name = "LIVE_INVOCATION_SERVER",value="anotherremotehost")
                }
            ),
            @DevTestVirtualServiceV3(
                serviceName = "vsV3_Data_Config_Update",
                workingFolder = "v3/activeconfig_data/new_config_data",
                dataFile = "Data.xlsx",
                activeConfig = "project.config",
                type = VirtualServiceV3Type.UPDATE
            )
        }
    )
    @Test
    public void vsV3_Data_Config_Update(){
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "8001","import/test/operation-8");
        assert (op8Response!=null);
        assert (op8Response.getValue("$.Id").equals("2"));
        assert (op8Response.getValue("$.Movie").equals("Iron Man"));
        assert (op8Response.getValue("$.LIVE_INVOCATION_SERVER").equals("remotehost"));
        assert (op8Response.getValue("$.LIVE_INVOCATION_PORT").equals("9000"));
    }
}

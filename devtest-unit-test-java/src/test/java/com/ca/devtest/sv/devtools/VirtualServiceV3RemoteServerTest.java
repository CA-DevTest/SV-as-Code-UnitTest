package com.ca.devtest.sv.devtools;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServiceV3;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;
import com.ca.devtest.sv.devtools.v3.HttpUtils;
import com.ca.devtest.sv.devtools.v3.ResponseParser;
import org.junit.Rule;
import org.junit.Test;

@DevTestVirtualServer( registryHost = "ussv-w2k19-itc2.dhcp.broadcom.net", groupName = "remote")
public class VirtualServiceV3RemoteServerTest {

    @Rule
    public VirtualServicesRule rules = new VirtualServicesRule();

    @DevTestVirtualServiceV3(
            serviceName = "vsV3_Deploy",
            port = "24778",
            workingFolder = "v3/rrpair",
            inputFile2 = "operation-8-req.txt",
            inputFile1 = "operation-8-rsp.txt"
    )
    @Test
    public void vsV3_Deploy(){
        ResponseParser responseParser = HttpUtils.GET(HttpUtils.URL_FORMAT,  "http","ussv-w2k19-itc2.dhcp.broadcom.net",
                "24778","import/test/operation-8");
        assert (responseParser!=null);
        assert (responseParser.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
    }
}

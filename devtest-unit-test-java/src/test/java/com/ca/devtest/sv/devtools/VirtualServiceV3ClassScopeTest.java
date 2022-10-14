package com.ca.devtest.sv.devtools;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServiceV3;
import com.ca.devtest.sv.devtools.junit.VirtualServiceClassScopeRule;
import com.ca.devtest.sv.devtools.v3.HttpUtils;
import com.ca.devtest.sv.devtools.v3.ResponseParser;
import org.junit.Rule;
import org.junit.Test;

@DevTestVirtualServer(deployServiceToVse = "VSE",groupName="V3UpdateTest", protocol = "http")
@DevTestVirtualServiceV3(
                serviceName = "vsV3_Verify",
                port = "24778",
                workingFolder = "v3/rrpair",
                inputFile2 = "operation-8-req.txt",
                inputFile1 = "operation-8-rsp.txt"
        )

public class VirtualServiceV3ClassScopeTest {
    @Rule
    public VirtualServiceClassScopeRule rules = new VirtualServiceClassScopeRule();

    @Test
    public void vsV3_Verify1(){
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778","import/test/operation-8");
        assert (op8Response!=null);
        assert (op8Response.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
    }

    @Test
    public void vsV3_Verify2(){
        ResponseParser op8Response = HttpUtils.GET(HttpUtils.URL_FORMAT, "http", "localhost",
                "24778","import/test/operation-8");
        assert (op8Response!=null);
        assert (op8Response.getValue("$.TCEntry[0].termsType").equals("Operation 8 terms"));
    }
}

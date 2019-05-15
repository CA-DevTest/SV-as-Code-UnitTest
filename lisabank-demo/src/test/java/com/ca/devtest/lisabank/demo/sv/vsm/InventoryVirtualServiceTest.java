package com.ca.devtest.lisabank.demo.sv.vsm;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.StoreServiceImp;
import com.ca.devtest.lisabank.demo.model.StoreInventory;
import com.ca.devtest.sv.devtools.annotation.*;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)
@DevTestVirtualServer(registryHost = "localhost",deployServiceToVse = "VSE", login = "admin", password="admin")
public class InventoryVirtualServiceTest {

    // handle VS with Class scope
    static final Log logger = LogFactory.getLog(com.ca.devtest.lisabank.demo.sv.vsm.InventoryVirtualServiceTest.class);
    @Autowired
    private StoreServiceImp storeService;

    @Rule
    public VirtualServicesRule rules = new VirtualServicesRule();

    @DevTestVirtualService(serviceName = "getStoresInventory", type = VirtualServiceType.VSM,
            workingFolder = "storeInventoryVSM", basePath = "/", parameters = {
            @Parameter(name = "port", value = "19804")},requestDataProtocol = {
            @Protocol(ProtocolType.DPH_JSON) },
            responseDataProtocol = {@Protocol(ProtocolType.DPH_JSON)})
    @Test
    public void getStoresInventory() {

        try {
            StoreInventory store = storeService.getStoreInventory();
            assertNotNull(store);
            printUsers(store);
            assertEquals(new Integer(1), store.getInteger_0());
        } finally {

        }

    }

    private void printUsers(StoreInventory store) {
       // for (StoreInventory inv : stores) {
            logger.info(store.getInteger_0());
       // }

    }

}

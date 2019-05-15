package com.ca.devtest.lisabank.demo.sv.http;

import com.ca.devtest.lisabank.demo.LisaBankClientApplication;
import com.ca.devtest.lisabank.demo.business.ProductService;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.Protocol;
import com.ca.devtest.sv.devtools.annotation.ProtocolType;
import com.ca.devtest.sv.devtools.junit.VirtualServicesRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.xml.bind.JAXBException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LisaBankClientApplication.class)
@DevTestVirtualServer()
public class ProductTest {

    static final Log logger = LogFactory.getLog(BasicTest.class);

    @Value("${webservice.url.product}")
    private String productServiceUrl;

    @Rule
    public VirtualServicesRule rules = new VirtualServicesRule();

    @Autowired
    private ProductService productService;

    @DevTestVirtualService(serviceName = "getProductByXML",
            basePath = "/",
            port = 9956,
            workingFolder = "template2",
            requestDataProtocol = {
                    @Protocol(ProtocolType.DPH_XML) })
    @Test
    public void getProductByXML() throws JAXBException {

        com.ca.devtest.lisabank.demo.business.Product  product = productService.getProduct(productServiceUrl);
        assertNotNull(product);
        logger.info(product.toString());
    }



}

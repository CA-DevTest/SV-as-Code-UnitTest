package com.ca.devtest.lisabank.demo.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.ca.devtest.lisabank.demo.business.Product;

@Component
public class ProductService {



    public Product getProduct(String uri) throws JAXBException{

        JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);

        InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        Product product = (Product) unmarshaller.unmarshal(stream);

        return product;


    }


}


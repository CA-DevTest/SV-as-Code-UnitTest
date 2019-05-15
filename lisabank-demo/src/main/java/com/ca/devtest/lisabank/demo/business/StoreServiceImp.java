package com.ca.devtest.lisabank.demo.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ca.devtest.lisabank.demo.model.StoreInventory;

@Component
public class StoreServiceImp {
  
  StoreInventory storeBean;
  
  @Value("${webservice.url.storeInventory}")
  private String storeServiceUrl;
  
  public StoreInventory getStoreInventory() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<StoreInventory> entity = new HttpEntity<StoreInventory>(headers);
    RestTemplate restTemplate = new RestTemplate();
    StoreInventory responseEntity =
        restTemplate.getForObject(storeServiceUrl, StoreInventory.class, HttpMethod.GET, entity);
    return responseEntity;
  }
  
}

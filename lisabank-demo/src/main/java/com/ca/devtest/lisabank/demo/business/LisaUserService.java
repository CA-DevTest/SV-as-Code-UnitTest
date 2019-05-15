package com.ca.devtest.lisabank.demo.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ca.devtest.lisabank.demo.model.LisaUser;

@Component
public class LisaUserService {
	LisaUser user; 
	
	@Value("${webservice.url.lisaUser}")
	private String lisaServiceUrl;
	
	public  List<LisaUser> getListUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LisaUser[]> entity = new HttpEntity<LisaUser[]>(headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<List<LisaUser>> response = restTemplate.exchange(lisaServiceUrl,
            HttpMethod.GET, entity,  new ParameterizedTypeReference<List<LisaUser>>(){});
	return response.getBody();
	}

}

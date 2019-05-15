package com.ca.devtest.lisabank.demo;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ca.devtest.lisabank.demo.business.HttpHeaderInterceptor;
import com.ca.devtest.lisabank.wsdl.EJB3AccountControlBean;
import com.ca.devtest.lisabank.wsdl.EJB3UserControlBean;
import com.ca.devtest.lisabank.wsdl.TokenBean;

/**
 * @author gaspa03
 *
 */
@Configuration
public class LisaBankClientConfiguration {
	@Value("${webservice.url.user}")
	private String userServiceAddress;
	@Value("${webservice.url.account}")
	private String accountServiceAddress;
	@Value("${webservice.url.token}")
	private String tokenServiceAddress;
 

	@Bean
	public EJB3UserControlBean userServiceClient() {

		JaxWsProxyFactoryBean jaxWsProxyFactory = new JaxWsProxyFactoryBean();
		
		jaxWsProxyFactory.setServiceClass(EJB3UserControlBean.class);
		jaxWsProxyFactory.setBus(springBus());
		jaxWsProxyFactory.setAddress(userServiceAddress);
		return (EJB3UserControlBean) jaxWsProxyFactory.create();

	}

	@Bean
	public EJB3AccountControlBean accountServiceClient() {

		JaxWsProxyFactoryBean jaxWsProxyFactory = new JaxWsProxyFactoryBean();
		jaxWsProxyFactory.setServiceClass(EJB3AccountControlBean.class);
		jaxWsProxyFactory.setBus(springBus());
		jaxWsProxyFactory.setAddress(accountServiceAddress);
		return (EJB3AccountControlBean) jaxWsProxyFactory.create();

	}
	
	@Bean
	public TokenBean tokenServiceClient() {

		JaxWsProxyFactoryBean jaxWsProxyFactory = new JaxWsProxyFactoryBean();
		jaxWsProxyFactory.setServiceClass(TokenBean.class);
		jaxWsProxyFactory.setAddress(tokenServiceAddress);
		return (TokenBean) jaxWsProxyFactory.create();
	}
	
	
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
	    SpringBus springBus = new SpringBus();
	    LoggingFeature logFeature = new LoggingFeature();
	    logFeature.setPrettyLogging(true);
	    logFeature.initialize(springBus);
	    springBus.getFeatures().add(logFeature);
	    springBus.getOutInterceptors().add(new HttpHeaderInterceptor(tokenServiceClient()));
	    return springBus;
	}
	
	
}

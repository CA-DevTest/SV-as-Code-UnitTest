package com.ca.devtest.lisabank.demo.business;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import com.ca.devtest.lisabank.wsdl.TokenBean;
public class HttpHeaderInterceptor extends AbstractPhaseInterceptor<Message> {
	String token = null;
	private TokenBean tokenService;
	public HttpHeaderInterceptor(TokenBean tokenService) {
		super(Phase.POST_LOGICAL);
		this.tokenService=tokenService;
	}

	public void handleMessage(Message message) {
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		authenticate();
		headers.put("Token", Arrays.asList(token));
		message.put(Message.PROTOCOL_HEADERS, headers);
	}
	
	private void authenticate(){
		//if(null==token)
			//token=tokenService.getNewToken("gpaco", "gpaco");
	}

}
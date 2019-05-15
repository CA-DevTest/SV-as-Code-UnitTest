/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServicesFromVrs;
import com.ca.devtest.sv.devtools.exception.VirtualServiceProcessorException;
import com.ca.devtest.sv.devtools.services.VirtualService;

/**
 * @author gaspa03
 *
 */
public class VirtualServicesFromVrsAnnotationProcessor implements AnnotationProcessor {


	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.annotation.processor.MethodProcessorAnnotation#process(com.ca.devtest.sv.devtools.DevTestClient, java.lang.annotation.Annotation)
	 */
	@Override
	public List<VirtualService> process(DevTestClient devTestClient, Annotation annotation)
			throws VirtualServiceProcessorException {
		 List<VirtualService>  result=new ArrayList<VirtualService>(1);
		 result.addAll(buildVirtualService(devTestClient, (DevTestVirtualServicesFromVrs) annotation));
		
		return result;
	}

	private Collection<? extends VirtualService> buildVirtualService(DevTestClient devTestClient,
			DevTestVirtualServicesFromVrs annotation) throws VirtualServiceProcessorException {
		DevTestVirtualServiceFromVrs[] virtualServicesAnnotation=annotation.value();
		List<VirtualService> virtualServices = new ArrayList<VirtualService>();
		
		for (DevTestVirtualServiceFromVrs vsAnnotation : virtualServicesAnnotation) {
			// get Annotation processor 
			AnnotationProcessor processor =AnnotationProcessorFactory.getInstance().getProcessor(vsAnnotation);
			List<VirtualService> services=processor.process(devTestClient,vsAnnotation);
			if( null!=services)
			virtualServices.addAll(services);
		}
		
		
		
		return virtualServices;
	}


}

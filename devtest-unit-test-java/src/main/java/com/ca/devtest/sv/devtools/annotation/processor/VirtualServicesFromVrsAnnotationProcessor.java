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
import com.ca.devtest.sv.devtools.services.AbstractVirtualService;

/**
 * @author gaspa03
 *
 */
public class VirtualServicesFromVrsAnnotationProcessor implements AnnotationProcessor {


	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.annotation.processor.MethodProcessorAnnotation#process(com.ca.devtest.sv.devtools.DevTestClient, java.lang.annotation.Annotation)
	 */
	@Override
	public List<AbstractVirtualService> process(DevTestClient devTestClient, Annotation annotation)
			throws Exception {
		 List<AbstractVirtualService>  result=new ArrayList<AbstractVirtualService>(1);
		 result.addAll(buildVirtualService(devTestClient, (DevTestVirtualServicesFromVrs) annotation));
		
		return result;
	}

	private Collection<? extends AbstractVirtualService> buildVirtualService(DevTestClient devTestClient,
			DevTestVirtualServicesFromVrs annotation) throws Exception {
		DevTestVirtualServiceFromVrs[] virtualServicesAnnotation=annotation.value();
		List<AbstractVirtualService> virtualServices = new ArrayList<AbstractVirtualService>();
		
		for (DevTestVirtualServiceFromVrs vsAnnotation : virtualServicesAnnotation) {
			// get Annotation processor 
			AnnotationProcessor processor =AnnotationProcessorFactory.getInstance().getProcessor(vsAnnotation);
			List<AbstractVirtualService> services=processor.process(devTestClient,vsAnnotation);
			if( null!=services)
			virtualServices.addAll(services);
		}
		
		
		
		return virtualServices;
	}


}

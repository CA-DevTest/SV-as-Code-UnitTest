/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServices;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;

/**
 * @author gaspa03
 *
 */
public class VirtualServicesAnnotationProcessor implements AnnotationProcessor {

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.annotation.processor.MethodProcessorAnnotation#process(com.ca.devtest.sv.devtools.DevTestClient, java.lang.annotation.Annotation)
	 */
	@Override
	public List<VirtualServiceInterface> process(DevTestClient devTestClient, Annotation annotation)
			throws Exception {
		 List<VirtualServiceInterface>  result=new ArrayList<VirtualServiceInterface>(1);
		 result.addAll(buildVirtualService(devTestClient, (DevTestVirtualServices) annotation));
		
		return result;
	}

	private Collection<? extends VirtualServiceInterface> buildVirtualService(DevTestClient devTestClient,
			DevTestVirtualServices annotation) throws Exception {
		DevTestVirtualService[] virtualServicesAnnotation=annotation.value();
		List<VirtualServiceInterface> virtualServices = new ArrayList<VirtualServiceInterface>();
		
		for (DevTestVirtualService vsAnnotation : virtualServicesAnnotation) {
			// get Annotation processor 
			AnnotationProcessor processor = AnnotationProcessorFactory.getInstance().getProcessor(vsAnnotation);
			List<VirtualServiceInterface> services=processor.process(devTestClient,vsAnnotation);
			if( null!=services)
			virtualServices.addAll(services);
		}

		return virtualServices;
	}

}

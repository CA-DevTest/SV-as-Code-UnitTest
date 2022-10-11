/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.exception.VirtualServiceProcessorException;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;

/**
 * @author gaspa03
 *
 */
public class NopAnnotationProcessor implements AnnotationProcessor {

	

	
	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.processor.MethodProcessorAnnotation#process()
	 */
	@Override
	public List<VirtualServiceInterface> process(DevTestClient devTestClient, Annotation annotation)throws VirtualServiceProcessorException {
		
		return new ArrayList<VirtualServiceInterface>();
	}

}

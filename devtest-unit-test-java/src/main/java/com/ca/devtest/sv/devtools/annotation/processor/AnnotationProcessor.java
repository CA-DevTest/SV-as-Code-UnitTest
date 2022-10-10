package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.List;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;

/**
 * @author gaspa03
 *
 */
public interface AnnotationProcessor {

	/**
	 * @return
	 */
	List<VirtualServiceInterface> process(DevTestClient devTestClient, Annotation annotation) throws Exception;

}

package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.List;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.services.AbstractVirtualService;

/**
 * @author gaspa03
 *
 */
public interface AnnotationProcessor {

	/**
	 * @return
	 */
	List<AbstractVirtualService> process(DevTestClient devTestClient, Annotation annotation) throws Exception;

}

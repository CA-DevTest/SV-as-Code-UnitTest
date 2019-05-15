package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServices;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServicesFromVrs;

/**
 * @author gaspa03
 *
 */
public class AnnotationProcessorFactory {

	private Map<Class, Class> dicoProcessor = new HashMap<Class, Class>();

	private static final AnnotationProcessorFactory INSTANCE = new AnnotationProcessorFactory();

	
	/**
	 * @return
	 */
	public static AnnotationProcessorFactory getInstance(){
		return INSTANCE;
	}
	
	private AnnotationProcessorFactory() {
		dicoProcessor.put(DevTestVirtualService.class, VirtualServiceAnnotationProcessor.class);
		dicoProcessor.put(DevTestVirtualServices.class, VirtualServicesAnnotationProcessor.class);
		dicoProcessor.put(DevTestVirtualServiceFromVrs.class, VirtualServiceFromVrsAnnotationProcessor.class);
		dicoProcessor.put(DevTestVirtualServicesFromVrs.class, VirtualServicesFromVrsAnnotationProcessor.class);
	}

	/**
	 * @param annotation
	 * @return
	 */
	public  AnnotationProcessor getProcessor(Annotation annotation) {
		AnnotationProcessor processor=null;
		if(dicoProcessor.containsKey(annotation.annotationType())){
			Class clazzProcessor= dicoProcessor.get(annotation.annotationType());
			try {
				processor=(AnnotationProcessor)clazzProcessor.newInstance();
			} catch (Exception e) {
				
			} 
		}else{
			processor=new NopAnnotationProcessor();
		}
		
		return processor;
	}

	
	/**
	 * @param annotation
	 * @param processor
	 */
	public void addProcessor(Class annotation, Class processor){
		dicoProcessor.put(annotation,processor);
	}
}

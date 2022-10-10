/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ca.devtest.sv.devtools.services.AbstractVirtualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.vse.VirtualServerEnvironment;
import com.ca.devtest.sv.devtools.vse.VirtualServerEnvironmentFactory;

/**
 * @author gaspa03
 *
 */
public class DevTestVirtualServerAnnotationProcessor {

	private final DevTestClient devtestClient;
	private final VirtualServerEnvironment vse;
	private static final Logger LOGGER = LoggerFactory.getLogger(DevTestVirtualServerAnnotationProcessor.class);
	

	/**
	 * @param ownerClazz
	 */
	public DevTestVirtualServerAnnotationProcessor(Class<?> ownerClazz) {
		super();
		devtestClient = buildDevtestClient(ownerClazz);
		vse= buildVSE(ownerClazz);
	}


	/**
	 * @param clazz
	 * @return
	 */
	private VirtualServerEnvironment buildVSE(Class<?> clazz) {
		DevTestVirtualServer virtualServer = clazz.getAnnotation(DevTestVirtualServer.class);
		return VirtualServerEnvironmentFactory.getVseInstance(virtualServer.registryHost(),virtualServer.deployServiceToVse());
	}


	/**
	 * @param clazz
	 * @return
	 */
	private DevTestClient buildDevtestClient(Class<?> clazz) {
		DevTestVirtualServer virtualServer = clazz.getAnnotation(DevTestVirtualServer.class);

		return new DevTestClient(virtualServer.protocol(), virtualServer.registryHost(), virtualServer.deployServiceToVse(),
				virtualServer.login(), virtualServer.password(), virtualServer.groupName(), virtualServer.keystore(),
				virtualServer.keystorePassword());
	}

	public List<AbstractVirtualService> process(Class<?> clazz) throws Exception {

		Annotation[] annotations = clazz.getDeclaredAnnotations();
		List<AbstractVirtualService> virtualServices = new ArrayList<AbstractVirtualService>();
		for (Annotation annotation : annotations) {
			// get Annotation processor
			AnnotationProcessor processor = AnnotationProcessorFactory.getInstance().getProcessor(annotation);
			List<AbstractVirtualService> services = processor.process(devtestClient, annotation);
			if (null != services)
				virtualServices.addAll(services);
		}

		return virtualServices;
	}

	public List<AbstractVirtualService> process(Method method) throws Exception {

		Annotation[] annotations = method.getDeclaredAnnotations();
		List<AbstractVirtualService> virtualServices = new ArrayList<AbstractVirtualService>();
		for (Annotation annotation : annotations) {
			// get Annotation processor
			AnnotationProcessor processor = AnnotationProcessorFactory.getInstance().getProcessor(annotation);
			List<AbstractVirtualService> services = processor.process(devtestClient, annotation);
			if (null != services)
				virtualServices.addAll(services);
		}

		return virtualServices;
	}

	/**
	 * @return Wrapper on VSE
	 */
	public VirtualServerEnvironment getVSE() {
		
		return vse;
	}

}

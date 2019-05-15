/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServiceFromVrs;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.exception.VirtualServiceProcessorException;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolFromVrsBuilder;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;
import com.ca.devtest.sv.devtools.utils.Utility;

/**
 * @author gaspa03
 *
 */
public class VirtualServiceFromVrsAnnotationProcessor implements AnnotationProcessor {

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.annotation.processor.MethodProcessorAnnotation#process(com.ca.devtest.sv.devtools.DevTestClient, java.lang.annotation.Annotation)
	 */
	@Override
	public List<VirtualService> process(DevTestClient devTestClient, Annotation annotation)
			throws VirtualServiceProcessorException {
		 List<VirtualService>  result=new ArrayList<VirtualService>(1);
		 result.add( buildVirtualService(devTestClient,(DevTestVirtualServiceFromVrs)annotation));
		return result;

	}
	
	/**
	 * @param devTestClient
	 * @param virtualService
	 * @return
	 * @throws VirtualServiceProcessorException
	 */
	private VirtualService buildVirtualService(DevTestClient devTestClient, DevTestVirtualServiceFromVrs virtualService)
			throws VirtualServiceProcessorException {
		try {
			URL url = getClass().getClassLoader().getResource(virtualService.workingFolder());
			File workingFolder = new File(url.toURI());
			
			VirtualServiceBuilder virtualServiceBuilder = devTestClient.fromRRPairs(virtualService.serviceName(),workingFolder);
			
			File vrsFile = null;
			if (!StringUtils.isEmpty(virtualService.vrsConfig().value())) {
				
				// if vrsConfig.value is specified, vrs file is loaded from this value
				vrsFile = new File(workingFolder, virtualService.vrsConfig().value());
			} else {
				
				// else we try to load a template in class path
				File tmpVrs = File.createTempFile(virtualService.serviceName(), "vrs");
				tmpVrs.deleteOnExit();
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template.vrs");
				FileOutputStream fileOutputStream = new FileOutputStream(tmpVrs);
				try {
					IOUtils.copy(inputStream, fileOutputStream);
				} catch (Exception error) {
					throw new VirtualServiceProcessorException(
							"Error while reading template.vrs file from classpath (maybe not found ?) : ", error);
				}
				inputStream.close();
				fileOutputStream.close();
				vrsFile = tmpVrs;
			}
			// Fin de rajout
			
			// handle Parameters and propagate parameters to Virtualservicebuilder
			Utility.addParamsToBuilder(virtualServiceBuilder, virtualService.parameters());
			
			// build Transport Protocol
			TransportProtocolFromVrsBuilder transportBuilder = new TransportProtocolFromVrsBuilder(vrsFile);
				Parameter[] transportParam = virtualService.vrsConfig().parameters();
				Utility.addParamsToBuilder(transportBuilder, transportParam);
				// add Transport Protocol
				virtualServiceBuilder.over(transportBuilder.build());
				virtualServiceBuilder.setCapacity(virtualService.capacity());
				virtualServiceBuilder.setAutoRestartEnabled(virtualService.autoRestartEnabled());
				virtualServiceBuilder.setExecutionMode(virtualService.executionMode());
				virtualServiceBuilder.setThinkScale(virtualService.thinkScale());
			return virtualServiceBuilder.build();
		} catch (Exception error) {
         throw new VirtualServiceProcessorException("Error during building virtual service : ", error);
		}

	}
	
}

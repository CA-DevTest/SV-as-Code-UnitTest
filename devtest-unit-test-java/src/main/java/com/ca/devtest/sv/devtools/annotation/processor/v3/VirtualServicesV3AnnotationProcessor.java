package com.ca.devtest.sv.devtools.annotation.processor.v3;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.processor.AnnotationProcessor;
import com.ca.devtest.sv.devtools.annotation.processor.AnnotationProcessorFactory;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServiceV3;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServicesV3;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sm632260
 *
 */
public class VirtualServicesV3AnnotationProcessor implements AnnotationProcessor {

    /* (non-Javadoc)
     * @see com.ca.devtest.sv.devtools.annotation.processor.MethodProcessorAnnotation#process(com.ca.devtest.sv.devtools.DevTestClient, java.lang.annotation.Annotation)
     */

    @Override
    public List<VirtualServiceInterface> process(DevTestClient devTestClient, Annotation annotation)
            throws Exception {
        List<VirtualServiceInterface>  result=new ArrayList<VirtualServiceInterface>(1);
        result.addAll(buildVirtualService(devTestClient, (DevTestVirtualServicesV3) annotation));

        return result;
    }

    /***
     * Builds and returns list of virtual services from Annotations
     *
     * @param devTestClient - devtest client
     * @param annotation - test annotations
     * @return
     * @throws Exception
     */
    private Collection<? extends VirtualServiceInterface> buildVirtualService(DevTestClient devTestClient,
                                                                             DevTestVirtualServicesV3 annotation) throws Exception {
        DevTestVirtualServiceV3[] virtualServicesAnnotation=annotation.value();
        List<VirtualServiceInterface> virtualServices = new ArrayList<VirtualServiceInterface>();

        for (DevTestVirtualServiceV3 vsAnnotation : virtualServicesAnnotation) {
            AnnotationProcessor processor = AnnotationProcessorFactory.getInstance().getProcessor(vsAnnotation);
            List<VirtualServiceInterface> services=processor.process(devTestClient,vsAnnotation);
            if( null!=services)
                virtualServices.addAll(services);
        }
        return virtualServices;
    }
}

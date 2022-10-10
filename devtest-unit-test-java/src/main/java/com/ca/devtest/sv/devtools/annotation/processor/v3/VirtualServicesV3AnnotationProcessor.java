package com.ca.devtest.sv.devtools.annotation.processor.v3;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.processor.AnnotationProcessor;
import com.ca.devtest.sv.devtools.annotation.processor.AnnotationProcessorFactory;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServiceV3;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServicesV3;
import com.ca.devtest.sv.devtools.services.AbstractVirtualService;

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
    public List<AbstractVirtualService> process(DevTestClient devTestClient, Annotation annotation)
            throws Exception {
        List<AbstractVirtualService>  result=new ArrayList<AbstractVirtualService>(1);
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
    private Collection<? extends AbstractVirtualService> buildVirtualService(DevTestClient devTestClient,
                                                                             DevTestVirtualServicesV3 annotation) throws Exception {
        DevTestVirtualServiceV3[] virtualServicesAnnotation=annotation.value();
        List<AbstractVirtualService> virtualServices = new ArrayList<AbstractVirtualService>();

        for (DevTestVirtualServiceV3 vsAnnotation : virtualServicesAnnotation) {
            AnnotationProcessor processor = AnnotationProcessorFactory.getInstance().getProcessor(vsAnnotation);
            List<AbstractVirtualService> services=processor.process(devTestClient,vsAnnotation);
            if( null!=services)
                virtualServices.addAll(services);
        }
        return virtualServices;
    }
}

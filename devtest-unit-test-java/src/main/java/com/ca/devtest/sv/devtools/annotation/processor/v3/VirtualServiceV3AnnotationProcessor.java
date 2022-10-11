package com.ca.devtest.sv.devtools.annotation.processor.v3;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.Parameter;
import com.ca.devtest.sv.devtools.annotation.processor.AnnotationProcessor;
import com.ca.devtest.sv.devtools.annotation.v3.DevTestVirtualServiceV3;
import com.ca.devtest.sv.devtools.annotation.v3.VirtualServiceV3Type;
import com.ca.devtest.sv.devtools.protocol.builder.ParamatrizedBuilder;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;
import com.ca.devtest.sv.devtools.services.builder.v3.VirtualServiceV3Builder;
import com.ca.devtest.sv.devtools.services.v3.ConfigObjectBuilder;
import com.ca.devtest.sv.devtools.utils.Utility;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sm632260
 *
 */
public class VirtualServiceV3AnnotationProcessor  implements AnnotationProcessor {
    @Override
    public List<VirtualServiceInterface> process(DevTestClient devTestClient, Annotation annotation) throws Exception {
        List<VirtualServiceInterface>  result=new ArrayList<VirtualServiceInterface>(1);
        result.add(buildVirtualService(devTestClient, (DevTestVirtualServiceV3) annotation));
        return result;
    }

    private VirtualServiceInterface buildVirtualService(DevTestClient devTestClient, DevTestVirtualServiceV3 virtualService)
            throws Exception {

            VirtualServiceV3Builder virtualServiceBuilder = null;
            validateAnnotations(virtualService, virtualService.type());
            URL url = getClass().getClassLoader().getResource(virtualService.workingFolder());
            File workingFolder = new File(url.toURI());
            if(virtualService.type().equals(VirtualServiceV3Type.CREATE)){
                virtualServiceBuilder = createService(devTestClient, virtualService, workingFolder);
            }else if(virtualService.type().equals(VirtualServiceV3Type.UPDATE)){
                virtualServiceBuilder = updateService(devTestClient,virtualService, workingFolder);
            }
            return virtualServiceBuilder.build();
    }

    private VirtualServiceV3Builder createService(DevTestClient devTestClient, DevTestVirtualServiceV3 virtualService,
                                                  File workingFolder) throws Exception{
        VirtualServiceV3Builder virtualServiceBuilder = devTestClient.withV3API(virtualService.serviceName(), workingFolder);
        virtualServiceBuilder.setConfig(virtualService.serviceName());
        String config = ConfigObjectBuilder.buildConfigJSON(virtualService, virtualServiceBuilder.getDeployedName());
        virtualServiceBuilder.setConfig(config);
        virtualServiceBuilder.addKeyValue(VirtualServiceV3Builder.LISTEN_PORT,virtualService.port());
        virtualServiceBuilder.addKeyValue(VirtualServiceV3Builder.DOCUMENTATION,virtualService.description());
        return initBuilder(virtualServiceBuilder, virtualService);
    }

    private VirtualServiceV3Builder updateService(DevTestClient devTestClient, DevTestVirtualServiceV3 virtualService,
                                                  File workingFolder) throws Exception{
        VirtualServiceV3Builder virtualServiceBuilder = devTestClient.withV3API(virtualService.serviceName(), workingFolder);
        String config = ConfigObjectBuilder.buildConfigJSON(virtualService, null);
        virtualServiceBuilder.setConfig(config);
        return initBuilder(virtualServiceBuilder, virtualService);
    }

    private VirtualServiceV3Builder initBuilder(VirtualServiceV3Builder builder, DevTestVirtualServiceV3 virtualService)
                                                                            throws Exception{
        builder.setType(virtualService.type().getType());
        builder.setUrl(virtualService.type().getUrlPattern());
        builder.setInputFile1(virtualService.inputFile1());
        builder.setInputFile2(virtualService.inputFile2());
        builder.setActiveConfig(virtualService.activeConfig());
        builder.setDataFile(virtualService.dataFile());
        builder.setRamlurl(virtualService.ramlurl());
        builder.setWadlurl(virtualService.wadlurl());
        builder.setSwaggerurl(virtualService.swaggerurl());
        addParamsToBuilder(builder, virtualService.parameters());
        return builder;
    }

    private void addParamsToBuilder(ParamatrizedBuilder builder, Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            builder.addKeyValue(parameter.name(), parameter.value());
        }
    }

    /***
     * Validates properties defined with annotation @DevTestVirtualSericeV3
     *
     * @param vs - test annotations for which properties should be validated
     * @param type - Virtual Service type
     */
    private void validateAnnotations(DevTestVirtualServiceV3 vs, VirtualServiceV3Type type){
        if(getClass().getClassLoader().getResource(vs.workingFolder()) == null){
            throw new IllegalArgumentException("Working folder does not exist");
        }else if(vs.serviceName().isEmpty()){
            throw new IllegalArgumentException("Service name can not be empty");
        }else if(type.equals(VirtualServiceV3Type.CREATE) && vs.port().isEmpty()){
            throw new IllegalArgumentException("Port number can not be empty");
        }else if(vs.type() == VirtualServiceV3Type.CREATE && vs.inputFile1().isEmpty() && vs.inputFile2().isEmpty() &&
                vs.ramlurl().isEmpty() && vs.swaggerurl().isEmpty() &&
                vs.wadlurl().isEmpty()){
            throw new IllegalArgumentException("All input fields can not be empty");
        }else if(!vs.inputFile1().isEmpty() && vs.inputFile1().equals(vs.inputFile2())){
            throw new IllegalArgumentException("Invalid argument same files are provided for inputFile1 and inputFile2");
        }else if(vs.inputFile1().endsWith(".vsi") && vs.inputFile2().isEmpty()){
            throw new IllegalArgumentException("Vsm file is not provided");
        }else if(vs.inputFile1().endsWith(".vsm") && vs.inputFile2().isEmpty()){
            throw new IllegalArgumentException("Vsi file is not provided");
        }else if(!vs.activeConfig().isEmpty() && !vs.activeConfig().endsWith(".config")){
            throw new IllegalArgumentException("Invalid active config provided");
        }else if(!vs.dataFile().isEmpty() && !vs.dataFile().endsWith(".zip")
                && !vs.dataFile().endsWith(".xlsx") && !vs.dataFile().endsWith(".csv")){
            throw new IllegalArgumentException("Invalid data file is provided");
        }else if(!vs.ramlurl().isEmpty() && !Utility.isUrl(vs.ramlurl())){
            throw new IllegalArgumentException("Raml url is not valid.");
        }else if(!vs.wadlurl().isEmpty() && !Utility.isUrl(vs.wadlurl())){
            throw new IllegalArgumentException("Wadl url is not valid.");
        }else if(!vs.swaggerurl().isEmpty() && !Utility.isUrl(vs.swaggerurl())){
            throw new IllegalArgumentException("Swagger url is not valid.");
        }
    }
}

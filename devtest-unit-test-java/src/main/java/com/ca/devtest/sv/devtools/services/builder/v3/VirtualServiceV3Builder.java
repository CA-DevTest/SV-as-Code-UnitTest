package com.ca.devtest.sv.devtools.services.builder.v3;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;
import com.ca.devtest.sv.devtools.annotation.Constants;
import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;
import com.ca.devtest.sv.devtools.services.builder.VirtualServiceBuilder;
import com.ca.devtest.sv.devtools.services.v3.VirtualServiceV3;
import com.ca.devtest.sv.devtools.utils.FileUtils;
import com.ca.devtest.sv.devtools.utils.VelocityRender;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;

/**
 * @author sm632260
 *
 */
public class VirtualServiceV3Builder extends VirtualServiceBuilder {

    public static final String LISTEN_PORT = "listenPort";
    public static final String DOCUMENTATION = "documentation";

    String config;
    String inputFile1;
    String inputFile2;
    String dataFile;
    String activeConfig;
    String swaggerurl;
    String ramlurl;
    String wadlurl;
    private VirtualServiceEnvironment vse;
    private final File workingFolder;

    public VirtualServiceV3Builder(String serviceName, VirtualServiceEnvironment vse, File workingFolder) {
        super(serviceName, vse);
        //this.vse = vse;
        this.workingFolder = workingFolder;
        this.setType(Constants.V3_API_CREATEVS);
        this.setUrl(Constants.V3_API_CREATEVS_URL);
    }

    /**
     * @return
     * @throws IOException
     */
    public final VirtualServiceInterface build() throws IOException {
        VirtualServiceV3 virtualService = new VirtualServiceV3(getServiceName(), getType(), getUrl(),getVse());
        virtualService.setInputFile1(updateFileWithParameters(virtualService,getAbsolutePath(getInputFile1())));
        virtualService.setInputFile2(updateFileWithParameters(virtualService,getAbsolutePath(getInputFile2())));
        virtualService.setActiveConfig(updateFileWithParameters(virtualService,getAbsolutePath(getActiveConfig())));
        virtualService.setDataFile(updateFileWithParameters(virtualService,getAbsolutePath(getDataFile())));
        virtualService.setActiveConfig(getAbsolutePath(getActiveConfig()));
        virtualService.setDataFile(getAbsolutePath(getDataFile()));
        virtualService.setRamlurl(getRamlurl());
        virtualService.setSwaggerurl(getSwaggerurl());
        virtualService.setWadlurl(getWadlurl());
        virtualService.setConfig(getConfig());
        virtualService.setDeployedName(getDeployedName());
        return virtualService;
    }

    @Override
    protected File packVirtualService() throws IOException {
        throw new RuntimeException("Not supported for VirtualServiceV3");
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getInputFile1() {
        return inputFile1;
    }

    public void setInputFile1(String inputFile1) {
        this.inputFile1 = inputFile1;
    }

    public String getInputFile2() {
        return inputFile2;
    }

    public void setInputFile2(String inputFile2) {
        this.inputFile2 = inputFile2;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public String getActiveConfig() {
        return activeConfig;
    }

    public void setActiveConfig(String activeConfig) {
        this.activeConfig = activeConfig;
    }

    public String getSwaggerurl() {
        return swaggerurl;
    }

    public void setSwaggerurl(String swaggerurl) {
        this.swaggerurl = swaggerurl;
    }

    public String getRamlurl() {
        return ramlurl;
    }

    public void setRamlurl(String ramlurl) {
        this.ramlurl = ramlurl;
    }

    public String getWadlurl() {
        return wadlurl;
    }

    public void setWadlurl(String wadlurl) {
        this.wadlurl = wadlurl;
    }

    public String getAbsolutePath(String path){
        return path != null && !path.isEmpty() ?
         new File(workingFolder, path).getAbsolutePath() :
        "";
    }

    private String updateFileWithParameters(VirtualServiceV3 virtualService, String inputFile) throws IOException {
        File file = new File(inputFile);
        if(file == null || !file.exists() || file.getAbsolutePath().endsWith(".zip")){
            return inputFile;
        }
        String fileName = FilenameUtils.getName(inputFile);
        String content = org.apache.commons.io.FileUtils.readFileToString(file,"UTF-8");
        content= VelocityRender.render(content, getParameters()) ;
        File tempFile =  FileUtils.crateTempFile(fileName,content.trim());
        virtualService.addUpdatedFile(tempFile);
        return tempFile.getAbsolutePath();
    }
}

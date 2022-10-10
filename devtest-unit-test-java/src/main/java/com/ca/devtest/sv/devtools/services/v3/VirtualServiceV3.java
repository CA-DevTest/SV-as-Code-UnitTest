package com.ca.devtest.sv.devtools.services.v3;

import com.ca.devtest.sv.devtools.VirtualServiceEnvironment;
import com.ca.devtest.sv.devtools.services.AbstractVirtualService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sm632260
 *
 */
public class VirtualServiceV3 extends AbstractVirtualService {
    List<File> updatedTempFiles;
    String config;
    String inputFile1;
    String inputFile2;
    String dataFile;
    String activeConfig;
    String swaggerurl;
    String ramlurl;
    String wadlurl;

    public VirtualServiceV3(String name, String type, String url, VirtualServiceEnvironment vse) {
        super(name,type, url, vse);
        updatedTempFiles = new ArrayList<>();
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

    public void addUpdatedFile(File updatedFile) {
        this.updatedTempFiles.add(updatedFile);
    }
    public void clean(){
        this.updatedTempFiles.forEach(file->{
            file.deleteOnExit();
        });
    }
}

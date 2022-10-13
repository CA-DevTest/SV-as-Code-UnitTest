package com.ca.devtest.sv.devtools;

import com.ca.devtest.sv.devtools.utils.CryptUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;


public class SVasCodeConfigHandler {
    public static final String ENCRYPT_VALUES_ENDING_WITH = "password";
    public static final String LOCAL_PROPERTY_FILE = "local-svascode.properties";
    public static final String DEFAULT_PROPERTY_FILE = "svascode.properties";
    public SVasCodeConfigHandler(){
    }

    /***
     * Returns true if properties encryption is successful else returns false
     *
     * @return boolean value
     */
    public boolean encryptProperties(){
       boolean localFileModified =  modifyPropertyFile(LOCAL_PROPERTY_FILE);
       boolean defaultFileModified = modifyPropertyFile(DEFAULT_PROPERTY_FILE);
       return localFileModified || defaultFileModified;
    }

    /***
     * Encrypts properties of given file
     *
     * @param propertyFile
     * @return
     */
    public boolean modifyPropertyFile(String propertyFile)  {
        URL url = getClass().getClassLoader().getResource(propertyFile);
        if(url == null ){
            return false;
        }
        AtomicBoolean needUpdate= new AtomicBoolean(false);
        FileInputStream fis = null;
        OutputStream fos = null;
        try {
            File file = new File(url.toURI());
            Properties properties = new Properties();
            fis=new FileInputStream(file);
            properties.load(fis);
            fis.close();
            properties.forEach((key, value)-> {
                if(key.toString().toLowerCase().endsWith(ENCRYPT_VALUES_ENDING_WITH)){
                    CryptUtil cryptUtil = new CryptUtil();
                    if(!cryptUtil.isEncrypted(value.toString())) {
                        properties.setProperty(key.toString(),cryptUtil.encrypt(value.toString()));
                        needUpdate.set(true);
                    }
                }
            });
            if(!needUpdate.get()){
                return false;
            }
            fos = new FileOutputStream(file);
            properties.store(fos,"encrypt properties");
        }catch (Exception e){
            return false;
        }finally {
            if(fis !=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}

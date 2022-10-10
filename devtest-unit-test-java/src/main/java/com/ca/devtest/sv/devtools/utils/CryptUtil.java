package com.ca.devtest.sv.devtools.utils;

import org.aeonbits.owner.crypto.AbstractEncryptor;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class CryptUtil extends AbstractEncryptor {

    private static final String CRYPT_STR_START = "$cry{";
    private static final String CRYPT_STR_END = "}";
    private static final String CRYPT_STR_FORMAT = "%s%s%s";

    private String algorithm = "AES";
    private String encoding = "UTF8";
    private String cryptStringFormat="cry(%s)";
    private byte[] secretKey = "svascodeunittest".getBytes();

    public CryptUtil(){
    }

    /***
     * Returns algorithun name
     *
     * @return
     */
    public String getAlgorithm() {
        return this.algorithm;
    }

    /***
     * Checks if given string is encrypted
     *
     * @param str - String to be checked
     * @return boolean value
     */
    public boolean isEncrypted(String str){
        return str.startsWith(CRYPT_STR_START) && str.endsWith(CRYPT_STR_END);
    }

    /***
     * Encrypts given strings
     *
     * @param plainData String to encrypt
     *
     * @return returns encrypted string
     */
    public String encrypt( String plainData ) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance( this.algorithm );
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal( plainData.getBytes( this.encoding ) );
            String encryptedValue = DatatypeConverter.printBase64Binary( encVal );
            return String.format(CRYPT_STR_FORMAT, CRYPT_STR_START,encryptedValue,CRYPT_STR_END);
        } catch ( Exception cause ) {
            throw new IllegalArgumentException( cause.getMessage(), cause );
        }
    }

    /***
     * Decrypts given string
     *
     * @param encryptedData - String to decrypt
     * @return - returns decrypted string
     * @throws IllegalArgumentException
     */
    public String decrypt(String encryptedData) throws IllegalArgumentException {
        String encryptedString = encryptedData;
        if(isEncrypted(encryptedData)){
            encryptedString = encryptedData.substring(CRYPT_STR_START.length(),
                    encryptedData.length()-CRYPT_STR_END.length());
        }
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance( this.algorithm );
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = DatatypeConverter.parseBase64Binary( encryptedString );
            byte[] decValue = c.doFinal(decodedValue);
            String decryptedValue = new String(decValue, this.encoding );
            return decryptedValue;
        } catch ( Exception cause ){
            throw new IllegalArgumentException( cause.getMessage(), cause );
        }
    }

    /***
     * Generats the keys for encryption and decryption
     *
     * @return - returns Key
     * @throws Exception
     */
    private Key generateKey() throws Exception {
        return new SecretKeySpec( this.secretKey, this.getAlgorithm() );
    }
}
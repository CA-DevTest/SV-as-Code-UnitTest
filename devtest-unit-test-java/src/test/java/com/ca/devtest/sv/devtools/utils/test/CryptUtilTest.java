package com.ca.devtest.sv.devtools.utils.test;

import com.ca.devtest.sv.devtools.utils.CryptUtil;
import org.junit.Test;

public class CryptUtilTest {
    CryptUtil cryptUtil = new CryptUtil();
    String decrypted = "SV-as-Code";
    String encrypted = "$cry{p8g2TAMpH4KsoHKxtMc2dA==}";

    @Test
    public void encrypt(){
        assert (cryptUtil.encrypt(decrypted).equals(encrypted));
    }

    @Test
    public void decrypt(){
        assert (cryptUtil.decrypt(encrypted).equals(decrypted));
    }

    @Test
    public void isEncrypted(){
        assert (cryptUtil.isEncrypted(encrypted));
    }

    @Test
    public void isEncrypted1(){
        assert (cryptUtil.isEncrypted(decrypted)==false);
    }

}

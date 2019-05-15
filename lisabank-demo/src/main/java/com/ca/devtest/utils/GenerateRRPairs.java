/**
 *
 */
package com.ca.devtest.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import com.ca.devtest.tools.rawtraffic.FromRawTrafficTestReferentialGenerator;

/**
 * @author gaspa03
 *
 */
public class GenerateRRPairs {
  
  /**
   * @param args
   */
  public static void main(String[] args)  {
     try {
      FromRawTrafficTestReferentialGenerator.generateRRPairFromRawFiles((GenerateRRPairs.class.getProtectionDomain().getCodeSource().getLocation().getPath()).replaceAll("%20", " ") + ".."
              + File.separatorChar + ".." + File.separatorChar + ".." + File.separatorChar
              + "lisabank-demo/src/test/resources/rawtraffic");
    } catch (XPathExpressionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }
  
}

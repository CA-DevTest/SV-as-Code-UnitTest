
package com.ca.devtest.lisabank.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ca.devtest.lisabank.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteTokenResponse_QNAME = new QName("http://ejb3.examples.itko.com/", "deleteTokenResponse");
    private final static QName _GetNewToken_QNAME = new QName("http://ejb3.examples.itko.com/", "getNewToken");
    private final static QName _DeleteToken_QNAME = new QName("http://ejb3.examples.itko.com/", "deleteToken");
    private final static QName _GetNewTokenResponse_QNAME = new QName("http://ejb3.examples.itko.com/", "getNewTokenResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ca.devtest.lisabank.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteTokenResponse }
     * 
     */
    public DeleteTokenResponse createDeleteTokenResponse() {
        return new DeleteTokenResponse();
    }

    /**
     * Create an instance of {@link GetNewToken }
     * 
     */
    public GetNewToken createGetNewToken() {
        return new GetNewToken();
    }

    /**
     * Create an instance of {@link DeleteToken }
     * 
     */
    public DeleteToken createDeleteToken() {
        return new DeleteToken();
    }

    /**
     * Create an instance of {@link GetNewTokenResponse }
     * 
     */
    public GetNewTokenResponse createGetNewTokenResponse() {
        return new GetNewTokenResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTokenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb3.examples.itko.com/", name = "deleteTokenResponse")
    public JAXBElement<DeleteTokenResponse> createDeleteTokenResponse(DeleteTokenResponse value) {
        return new JAXBElement<DeleteTokenResponse>(_DeleteTokenResponse_QNAME, DeleteTokenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewToken }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb3.examples.itko.com/", name = "getNewToken")
    public JAXBElement<GetNewToken> createGetNewToken(GetNewToken value) {
        return new JAXBElement<GetNewToken>(_GetNewToken_QNAME, GetNewToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteToken }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb3.examples.itko.com/", name = "deleteToken")
    public JAXBElement<DeleteToken> createDeleteToken(DeleteToken value) {
        return new JAXBElement<DeleteToken>(_DeleteToken_QNAME, DeleteToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewTokenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb3.examples.itko.com/", name = "getNewTokenResponse")
    public JAXBElement<GetNewTokenResponse> createGetNewTokenResponse(GetNewTokenResponse value) {
        return new JAXBElement<GetNewTokenResponse>(_GetNewTokenResponse_QNAME, GetNewTokenResponse.class, null, value);
    }

}

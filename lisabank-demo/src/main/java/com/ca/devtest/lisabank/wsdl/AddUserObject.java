
package com.ca.devtest.lisabank.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addUserObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addUserObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userObject" type="{http://ejb3.examples.itko.com/}user" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addUserObject", propOrder = {
    "userObject"
})
public class AddUserObject {

    protected User userObject;

    /**
     * Gets the value of the userObject property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUserObject() {
        return userObject;
    }

    /**
     * Sets the value of the userObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUserObject(User value) {
        this.userObject = value;
    }

}

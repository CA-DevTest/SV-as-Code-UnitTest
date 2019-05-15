
package com.ca.devtest.lisabank.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="accountType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CHECKING"/>
 *     &lt;enumeration value="SAVINGS"/>
 *     &lt;enumeration value="CREDIT"/>
 *     &lt;enumeration value="AUTO_LOAN"/>
 *     &lt;enumeration value="STUDENT_LOAN"/>
 *     &lt;enumeration value="MORTGAGE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "accountType")
@XmlEnum
public enum AccountType {

    CHECKING,
    SAVINGS,
    CREDIT,
    AUTO_LOAN,
    STUDENT_LOAN,
    MORTGAGE;

    public String value() {
        return name();
    }

    public static AccountType fromValue(String v) {
        return valueOf(v);
    }

}

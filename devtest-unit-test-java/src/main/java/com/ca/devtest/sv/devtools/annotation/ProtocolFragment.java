/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gaspa03
 * refer to a fragment file with protocol definition
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ProtocolFragment {
	public String value() ;
	Parameter[] parameters() default {};
}

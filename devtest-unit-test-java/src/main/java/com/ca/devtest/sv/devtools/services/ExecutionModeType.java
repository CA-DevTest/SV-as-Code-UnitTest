/**
 * 
 */
package com.ca.devtest.sv.devtools.services;

/**
 * @author gaspa03
 *
 */
public enum ExecutionModeType {
	EFFICIENT, LIVE, TRACK, STAND_IN, FAILOVER, DYNAMIC, LEARNING;


	public static ExecutionModeType valueIgnoreCaseOf(String  executionMode){
		ExecutionModeType result=EFFICIENT;
		if ((executionMode == null) || (executionMode.length() == 0)) {
		     return result;
	   }
		for (ExecutionModeType mode : values()) {
			     if (mode.name().equalsIgnoreCase(executionMode)) {
			    	 result= mode;
			    	 break;
			     }
		 }
		 return result;
	}
	
}

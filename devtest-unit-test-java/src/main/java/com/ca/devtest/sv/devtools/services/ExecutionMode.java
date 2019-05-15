/**
 * 
 */
package com.ca.devtest.sv.devtools.services;

/**
 * @author gaspa03
 *
 */
public class ExecutionMode {

	private int capacity=1;
    private int thinkScale=100;
    private boolean autoRestartEnabled=true;
    private ExecutionModeType executionMode=ExecutionModeType.EFFICIENT;
	/**
	 * @return the capacity
	 */
	public final int getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public final void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the thinkScale
	 */
	public final int getThinkScale() {
		return thinkScale;
	}
	/**
	 * @param thinkScale the thinkScale to set
	 */
	public final void setThinkScale(int thinkScale) {
		this.thinkScale = thinkScale;
	}
	/**
	 * @return the autoRestartEnabled
	 */
	public final boolean isAutoRestartEnabled() {
		return autoRestartEnabled;
	}
	/**
	 * @param autoRestartEnabled the autoRestartEnabled to set
	 */
	public final void setAutoRestartEnabled(boolean autoRestartEnabled) {
		this.autoRestartEnabled = autoRestartEnabled;
	}
	/**
	 * @return the executionMode
	 */
	public final ExecutionModeType getExecutionMode() {
		return executionMode;
	}
	/**
	 * @param executionMode the executionMode to set
	 */
	public final void setExecutionMode(ExecutionModeType executionMode) {
		this.executionMode = executionMode;
	}
    
	
}

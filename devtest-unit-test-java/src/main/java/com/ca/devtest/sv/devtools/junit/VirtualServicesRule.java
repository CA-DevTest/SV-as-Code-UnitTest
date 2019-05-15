/**
 * 
 */
package com.ca.devtest.sv.devtools.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.processor.DevTestVirtualServerAnnotationProcessor;
import com.ca.devtest.sv.devtools.services.VirtualService;

/**
 * Extend JUnit behaviour for using virtual services. <br/>
 *  
 * Allow virtual services to be deployed before test methods and undeployed after. 
 * 
 * @author gaspa03, bboulch
 *
 */
public class VirtualServicesRule implements TestRule {

	private static final Logger LOGGER = LoggerFactory.getLogger(VirtualServicesRule.class);

	
	public  VirtualServicesRule() {
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement,
	 * org.junit.runner.Description)
	 */
	public Statement apply(final Statement base, final Description description) {

		
		
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				if (!clazzNeedVirtualServices(description.getTestClass())) {
					LOGGER.info(description.getTestClass() + "is not annoted by DevTestVirtualServer");
					base.evaluate();
				} else {
					
					List<VirtualService> virtualServices = null;
					try {
						
						LOGGER.info("deploying VS for method "+ description.getMethodName()+"......");
						virtualServices = processMethodAnnotations(description);
						deployVirtualServices(virtualServices);
						base.evaluate();
					} finally {
						Thread.sleep(500);
						LOGGER.info(".... undeploying VS for method "+ description.getMethodName());
						unDeployVirtualServices(virtualServices);
					}

				}
			}

		};

	}

	/**
	 * @param virtualServices list of virtual services to deploy
	 */
	private void deployVirtualServices(List<VirtualService> virtualServices) {

		if (null != virtualServices) {
			for (VirtualService virtualService : virtualServices) {
				try {
					LOGGER.debug("Deploy virtual service " + virtualService.getName() + ".....");
					virtualService.deploy();
					LOGGER.debug("Virtual service " + virtualService.getName() + " deployed!");
				} catch (Exception error) {
				   throw new RuntimeException("Error when try to deploy Virtual Service  " + virtualService.getName(), error);
				}
			}
		}

	}

	/**
	 * 
	 * @param virtualServices list of virtual services to undeployy
	 */
	private void unDeployVirtualServices(Collection<VirtualService> virtualServices) {

		if (null != virtualServices) {
			for (VirtualService virtualService : virtualServices) {
				try {
					LOGGER.debug("unDeploy virtual service " + virtualService.getName() + ".....");
					virtualService.unDeploy();
					LOGGER.debug("Virtual service " + virtualService.getName() + " unDeployed!");

				} catch (Exception error) {

					throw new RuntimeException(
							"Error when try to unDeploy Virtual Service  " + virtualService.getName(), error);

				}
			}
		}

	}

	/**
	 *  Find out SV annotation on method level
	 * @param testClass
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private List<VirtualService> processMethodAnnotations(Description description) {
		List<VirtualService> virtualServices = new ArrayList<VirtualService>();
		try {
			 LOGGER.debug("Process annotation for method "+description.getMethodName());
			Class<?> testClazz = description.getTestClass();
			DevTestVirtualServerAnnotationProcessor devtestProcessor=new DevTestVirtualServerAnnotationProcessor(testClazz);
			Method method = testClazz.getMethod(description.getMethodName(), new Class[] {});
			virtualServices.addAll(devtestProcessor.process(method));
			
			
			
		} catch (Exception error) {

			throw new RuntimeException("Error when try to build Virtual Service over " + description.getDisplayName(),
					error);
		}

		return virtualServices;

	}

	
	/**
	 * @param testClass
	 * @return
	 */
	private boolean clazzNeedVirtualServices(Class<?> clazz) {

		return null != clazz.getAnnotation(DevTestVirtualServer.class);

	}
	
	@Override
	/*
	 * Undeploy Virtual service with scope classes
	 * */
	protected void finalize() throws Throwable {
		
		
		super.finalize();
	}
}

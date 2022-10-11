/**
 * 
 */
package com.ca.devtest.sv.devtools.junit;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ca.devtest.sv.devtools.services.VirtualServiceInterface;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServer;
import com.ca.devtest.sv.devtools.annotation.processor.DevTestVirtualServerAnnotationProcessor;

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
				List<VirtualServiceInterface> deployedServices = new ArrayList<>();
				boolean evaluate = true;
				if (!clazzNeedVirtualServices(description.getTestClass())) {
					LOGGER.info(description.getTestClass() + "is not annoted by DevTestVirtualServer");
					base.evaluate();
				} else {
					List<VirtualServiceInterface> virtualServices = null;
					try {
						LOGGER.info("deploying VS for method " + description.getMethodName() + "......");
						virtualServices = processMethodAnnotations(description);
						deployedServices = deployVirtualServices(virtualServices);
					} catch (RuntimeException e){
						evaluate = false;
						throw e;
					}
					try{
						if(evaluate) {
							base.evaluate();
						}
					}finally{
						 Thread.sleep(500);
						 LOGGER.info(".... undeploying VS for method " + description.getMethodName());
						 unDeployVirtualServices(deployedServices);
					}
				}
			}

		};

	}

	/**
	 * @param virtualServices list of virtual services to deploy
	 */
	private List<VirtualServiceInterface> deployVirtualServices(List<VirtualServiceInterface> virtualServices) throws
			Exception {
		List<VirtualServiceInterface> deployedVirtualServices = new ArrayList<>();
		if (null != virtualServices) {
			for (VirtualServiceInterface virtualService : virtualServices) {
					LOGGER.debug("Deploy virtual service " + virtualService.getName() + ".....");
					virtualService.deploy();
					deployedVirtualServices.add(virtualService);
					LOGGER.debug("Virtual service " + virtualService.getName() + " deployed!");
			}
		}
		return deployedVirtualServices;
	}

	/**
	 * 
	 * @param virtualServices list of virtual services to undeployy
	 */
	private void unDeployVirtualServices(Collection<VirtualServiceInterface> virtualServices) throws
			Exception {

		if (null != virtualServices) {
			List<String> undeployedServices = new ArrayList<>();
			for (VirtualServiceInterface virtualService : virtualServices) {
					if(undeployedServices.contains(virtualService.getDeployedName())){
						LOGGER.info("Virtual service is already undeployed "+virtualService.getName());
						continue;
					}
					LOGGER.debug("unDeploy virtual service " + virtualService.getName() + ".....");
					virtualService.unDeploy();
					LOGGER.debug("Virtual service " + virtualService.getName() + " unDeployed!");
					undeployedServices.add(virtualService.getDeployedName());
			}
		}

	}

	/**
	 *  Find out SV annotation on method level
	 * @param description
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private List<VirtualServiceInterface> processMethodAnnotations(Description description) throws Exception {
		List<VirtualServiceInterface> virtualServices = new ArrayList<VirtualServiceInterface>();
		 LOGGER.debug("Process annotation for method "+description.getMethodName());
		Class<?> testClazz = description.getTestClass();
		DevTestVirtualServerAnnotationProcessor devtestProcessor=new DevTestVirtualServerAnnotationProcessor(testClazz);
		Method method = testClazz.getMethod(description.getMethodName());
		virtualServices.addAll(devtestProcessor.process(method));
		return virtualServices;
	}

	
	/**
	 * @param clazz
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

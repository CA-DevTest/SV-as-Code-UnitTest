/**
 * 
 */
package com.ca.devtest.sv.devtools.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author gaspa03
 *
 */
public class VelocityRender {

	/**
	 * @param content
	 * @param config
	 * @return
	 */
	public static String render(String content, Map config) {
		// Initialize the engine.
		VelocityEngine engine = new VelocityEngine();

		engine.setProperty(Velocity.RESOURCE_LOADER, "string");
		engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
		
		engine.addProperty("string.resource.loader.repository.static", "false");
		engine.init();

		// Initialize my template repository. You can replace the "Hello $w"
		// with your String.
		StringResourceRepository repo = (StringResourceRepository) engine
				.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
		repo.putStringResource("vsi.velocity.template", content);

		// Set parameters for my template.
		VelocityContext context = createContext(config);

		// Get and merge the template with my parameters.
		Template template = engine.getTemplate("vsi.velocity.template");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);


		return writer.toString();
	}

	/**
	 * @param config
	 * @return
	 */
	private static VelocityContext createContext (Map<String, Object> config)
	{
		return new  VelocityContext( config);
	}
	
	
}

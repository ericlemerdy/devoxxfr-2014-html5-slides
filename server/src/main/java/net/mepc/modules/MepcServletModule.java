package net.mepc.modules;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class MepcServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		super.configureServlets();
		bind(GuiceFilter.class);
		bind(GuiceContainer.class);
		Map<String, String> guiceContainerConfig = new HashMap<String, String>();
		guiceContainerConfig.put(ResourceConfig.FEATURE_CANONICALIZE_URI_PATH, "TRUE");
		guiceContainerConfig.put(JSONConfiguration.FEATURE_POJO_MAPPING, "TRUE");
		serve("/*").with(GuiceContainer.class, guiceContainerConfig);
	}
}
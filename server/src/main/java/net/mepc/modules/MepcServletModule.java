package net.mepc.modules;

import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import net.mepc.filter.CORSResponseFilter;

import java.util.HashMap;
import java.util.Map;

public class MepcServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		super.configureServlets();
		bind(ContainerResponseFilter.class).to(CORSResponseFilter.class).asEagerSingleton();
		bind(GuiceFilter.class);
		bind(GuiceContainer.class);
		Map<String, String> guiceContainerConfig = new HashMap<String, String>();
		guiceContainerConfig.put(ResourceConfig.FEATURE_CANONICALIZE_URI_PATH, "TRUE");
		guiceContainerConfig.put(JSONConfiguration.FEATURE_POJO_MAPPING, "TRUE");
		guiceContainerConfig.put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, net.mepc.filter.CORSResponseFilter.class.getName());
		serve("/*").with(GuiceContainer.class, guiceContainerConfig);
	}
}
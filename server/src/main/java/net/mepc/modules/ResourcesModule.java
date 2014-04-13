package net.mepc.modules;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import net.mepc.resources.TodoResource;


public class ResourcesModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(TodoResource.class);
	}

	@Provides
	@Singleton
	ObjectMapper objectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}

	@Provides
	@Singleton
	JacksonJsonProvider jacksonJsonProvider(ObjectMapper mapper) {
		return new JacksonJsonProvider(mapper);
	}

}

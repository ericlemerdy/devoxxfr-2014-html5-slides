package net.mepc.modules;

import net.mepc.resources.AdminResource;
import net.mepc.resources.BookResource;
import net.mepc.resources.OrderResource;
import net.mepc.resources.PublicResource;
import net.mepc.resources.StatusResource;
import net.mepc.resources.StoryResource;
import net.mepc.resources.UserResource;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;


public class ResourcesModule extends AbstractModule {

	@Override
	protected void configure() {
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

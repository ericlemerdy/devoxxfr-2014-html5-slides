package net.mepc.repository.db;

import static net.mepc.repository.db.MongoConfiguration.createConfiguration;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MongoConfigurationTest {
	@Test
	public void with_no_address_and_no_port_should_return_default_uri() {
		MongoConfiguration configuration = createConfiguration();

		assertThat(configuration.getUri().getURI()).isEqualTo("mongodb://127.0.0.1:27017");
	}
}

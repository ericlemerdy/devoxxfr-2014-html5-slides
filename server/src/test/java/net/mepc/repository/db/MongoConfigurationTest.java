package net.mepc.repository.db;

import static net.mepc.repository.db.MongoConfiguration.createConfiguration;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.mongodb.MongoClientURI;

public class MongoConfigurationTest {
	@Test
	public void with_no_address_and_no_port_should_return_default_uri() {
		MongoClientURI mongoURI = new MongoClientURI("mongodb://127.0.0.1:27017");

		MongoConfiguration configuration = createConfiguration();

		assertThat(configuration.getUri().getURI()).isEqualTo(mongoURI.getURI());
	}
}

package net.mepc.repository.db;

import static net.mepc.repository.db.MongoConfiguration.createConfiguration;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MongoConfigurationTest {
	@Test
	public void with_no_address_and_no_port_should_return_default_uri() {
		MongoConfiguration configuration = createConfiguration();

		assertThat(configuration.getUri().getURI()).isEqualTo("mongodb://172.17.0.4:27017");
	}


    @Test
    public void with_env_var_and_no_port_should_return_specified_uri() {
        MongoConfiguration configuration = new MongoConfiguration(new SystemEnvironmentProvider() {
            @Override
            public String get(String name) {
                return "bar";
            }
        });

        assertThat(configuration.getUri().getURI()).isEqualTo("mongodb://bar:27017");
    }
}

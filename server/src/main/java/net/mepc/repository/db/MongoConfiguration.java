package net.mepc.repository.db;

import static com.google.common.base.Objects.firstNonNull;
import static java.text.MessageFormat.format;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.mongodb.MongoClientURI;

public class MongoConfiguration {
	private static MongoConfiguration configuration = new MongoConfiguration(new SystemEnvironmentProvider());

	private long port = 27017l;

	private String address = "127.0.0.1";

	private String db;

    private SystemEnvironmentProvider systemEnvironmentProvider;

    @Inject
    protected MongoConfiguration(SystemEnvironmentProvider systemEnvironmentProvider) {
        this.systemEnvironmentProvider = systemEnvironmentProvider;
        this.address = firstNonNull(systemEnvironmentProvider.get("DB_PORT_27017_TCP_ADDR"), "127.0.0.1");
    }

	public static MongoConfiguration createConfiguration() {
		return configuration;
	}

	public static MongoConfiguration createConfiguration(String db) {
		return configuration.setDb(db);
	}

	public MongoConfiguration setPort(long port) {
		this.port = port;
		return this;
	}

	public MongoConfiguration setAddress(String address) {
		this.address = address;
		return this;
	}

	public MongoConfiguration setDb(String db) {
		this.db = db;
		return this;
	}

	MongoClientURI getUri() {
		return new MongoClientURI(format("mongodb://{0}{1}:{2}{3}{4}", "", address, String.valueOf(port), "", ""));
	}

	String getDb() {
		return this.db;
	}

}

package net.mepc.repository.db;

import com.google.inject.Inject;
import com.mongodb.MongoClientURI;

import static java.text.MessageFormat.format;

public class MongoConfiguration {
	private static MongoConfiguration configuration = new MongoConfiguration();

	private long port = 27017l;

	private String address = "127.0.0.1";

	private String db;

	@Inject
	private MongoConfiguration() {
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

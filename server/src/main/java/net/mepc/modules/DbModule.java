package net.mepc.modules;

import net.mepc.repository.db.MongoConfiguration;
import net.mepc.repository.db.MongoServer;

import com.google.inject.AbstractModule;


public class DbModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MongoServer.class).asEagerSingleton();
		bind(MongoConfiguration.class).toInstance(MongoConfiguration.createConfiguration("mepc"));
	}

}

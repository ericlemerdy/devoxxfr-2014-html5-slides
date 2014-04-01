package net.mepc.modules;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import net.mepc.modules.logger.SLF4JTypeListener;
import net.mepc.server.MepcServer;
import net.mepc.server.ServerConfig;


public class ServerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ServerConfig.class);
		bind(MepcServer.class);
		bindListener(Matchers.any(), new SLF4JTypeListener());
	}
}

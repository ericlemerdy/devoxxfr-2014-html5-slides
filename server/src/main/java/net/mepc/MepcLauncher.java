package net.mepc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.sli4j.slf4j.Slf4jLoggingModule;
import net.mepc.modules.*;
import net.mepc.server.MepcServer;

import static com.google.inject.matcher.Matchers.any;


public class MepcLauncher {

	public static void main(String[] args) throws Exception {
		System.setProperty("test_mode", String.valueOf(args.length > 0 && args[0].equals("test")));
		Injector injector = getInjector();
		MepcServer server = injector.getInstance(MepcServer.class);
		server.start();
	}

	public static Injector getInjector() {
		Injector injector = Guice.createInjector( //
				new ConfigModule(), //
				new MepcServletModule(), //
				new DbModule(), //
				new ResourcesModule(), //
				new ServerModule(), //
				new Slf4jLoggingModule(any()));

		return injector;
	}
}

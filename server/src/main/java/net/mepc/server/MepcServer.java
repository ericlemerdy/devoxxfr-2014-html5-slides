package net.mepc.server;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import ch.qos.logback.access.jetty.RequestLogImpl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;

import java.util.EnumSet;

public class MepcServer {

	private Server server;

	@Inject
	public MepcServer(GuiceFilter filter, ServerConfig serverConfig,
			@Named("logback_access_config") String logbackAccessConfig) {
		HandlerCollection handlers = new HandlerCollection();

		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setContextPath(serverConfig.getContextRoot());
		contextHandler.addFilter(new FilterHolder(filter), "/*", EnumSet.allOf(DispatcherType.class));
		handlers.addHandler(contextHandler);

		RequestLogHandler logHandler = new RequestLogHandler();
		RequestLogImpl requestLog = new RequestLogImpl();
		requestLog.setFileName(logbackAccessConfig);
		logHandler.setRequestLog(requestLog);
		handlers.addHandler(logHandler);

		server = new Server(serverConfig.getPort());
		server.setHandler(handlers);
	}

	public void start() throws Exception {
		server.start();
	}
}

package net.mepc.resources;

import org.slf4j.Logger;

import net.mepc.server.ServerConfig;


public abstract class AbstractResource {

	protected ServerConfig serverConfig;
	protected Logger logger;

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}

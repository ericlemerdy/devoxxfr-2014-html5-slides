package net.mepc.resources;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import net.mepc.server.ServerConfig;

import org.slf4j.Logger;


public abstract class AbstractResource {

	protected ServerConfig serverConfig;
	protected Logger logger;

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}

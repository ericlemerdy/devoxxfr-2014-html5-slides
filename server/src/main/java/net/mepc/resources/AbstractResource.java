package net.mepc.resources;

import org.slf4j.Logger;


public abstract class AbstractResource {

	protected Logger logger;

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}

package net.mepc.server;

import java.nio.file.FileAlreadyExistsException;

public class ServerConfigBuilder {
	private static ServerConfigBuilder builder = new ServerConfigBuilder();

	private int port;
	private String contextRoot;
	private String workingDirectoryPath;
	private String assetsDirectoryPath;

	private ServerConfigBuilder() {
		port = 8080;
		contextRoot = "";
		workingDirectoryPath = "";
		assetsDirectoryPath = "";
	}

	public static ServerConfigBuilder initServerConfig() {
		return builder;
	}

	public ServerConfigBuilder withPort(int port) {
		this.port = port;
		return this;
	}

	public ServerConfigBuilder withWorkingDirectory(String path) {
		this.workingDirectoryPath = path;
		return this;
	}

	public ServerConfig build() throws FileAlreadyExistsException {
		return new ServerConfig(port, contextRoot, workingDirectoryPath, assetsDirectoryPath, true);
	}

	public ServerConfigBuilder withContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
		return this;
	}
}

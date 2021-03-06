package net.mepc.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

public class ServerConfig {
	private final int port;
	private final String contextRoot;
	private File workingDirectory;
	private File assetsDirectory;

	@Inject
	public ServerConfig(@Named("listen_port") int port, @Named("context_root") String contextRoot,
			@Named("working_dir") String workingDirectoryPath, @Named("assets_dir") String assetsDirectoryPath) throws FileAlreadyExistsException {
		this.port = port;
		this.contextRoot = contextRoot;
		this.assetsDirectory = new File(assetsDirectoryPath);
		if (this.assetsDirectory.exists()) {
			if (!this.assetsDirectory.isDirectory()) {
				throw new FileAlreadyExistsException(assetsDirectoryPath, null,
						"File already exists and is not a directory.");
			}
		} else {
			this.assetsDirectory.mkdirs();
		}

		this.workingDirectory = new File(workingDirectoryPath);
		if (this.workingDirectory.exists()) {
			if (!this.workingDirectory.isDirectory()) {
				throw new FileAlreadyExistsException(workingDirectoryPath, null,
						"File already exists and is not a directory.");
			}
		} else {
			this.workingDirectory.mkdirs();
		}
	}

	public File getAssetsDirectory() {
		return assetsDirectory;
	}

	public int getPort() {
		return port;
	}

	public String getContextRoot() {
		return contextRoot;
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

}

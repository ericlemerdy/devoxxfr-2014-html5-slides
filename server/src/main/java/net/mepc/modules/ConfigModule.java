package net.mepc.modules;

import static com.google.common.io.Files.newReader;
import static com.google.common.io.Resources.getResource;
import static com.google.inject.name.Names.bindProperties;
import static java.lang.Boolean.getBoolean;
import static java.lang.System.getenv;
import static java.nio.charset.Charset.defaultCharset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import net.mepc.config.MepcConfig;

import com.google.inject.AbstractModule;


public class ConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		String configPath = getBoolean("test_mode") ? getResource("mepc.config").getPath()
				: getenv("MEPC_CONFIG_PATH");
		File configFile = new File(configPath);
		Properties properties = new Properties();
		try {
			properties.load(newReader(configFile, defaultCharset()));
			bindProperties(binder(), properties);
		} catch (FileNotFoundException e) {
			System.err.println("The configuration file " + configFile.getAbsolutePath()
					+ " can not be found or is not a file.");
		} catch (IOException e) {
			System.err.println("I/O error during loading configuration from file " + configFile.getAbsolutePath());
		}
		bind(MepcConfig.class);
	}
}

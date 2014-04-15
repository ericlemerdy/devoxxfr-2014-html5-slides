package net.mepc.modules;

import static com.google.common.io.Files.newReader;
import static com.google.common.io.Resources.getResource;
import static com.google.inject.name.Names.bindProperties;
import static java.lang.Boolean.getBoolean;
import static java.lang.String.format;
import static java.lang.System.err;
import static java.lang.System.getenv;
import static java.nio.charset.Charset.defaultCharset;

import com.google.inject.AbstractModule;

import net.mepc.config.MepcConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		String configPath = getenv("MEPC_CONFIG_PATH");
		Properties properties = new Properties();
		try {
			if (configPath != null) {
				File configFile = new File(configPath);
				try {
					properties.load(newReader(configFile, defaultCharset()));
				} catch (FileNotFoundException e) {
					err.println(format("The configuration file %s can not be found or is not a file.", configFile.getAbsolutePath()));
				} 
			} else {
					properties.load(this.getClass().getResourceAsStream("/mepc.config"));
			}
		} catch (IOException e) {
			err.println("I/O error during loading configuration");
		}
		bindProperties(binder(), properties);
		bind(MepcConfig.class);
	}
}

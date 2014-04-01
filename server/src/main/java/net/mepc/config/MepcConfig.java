package net.mepc.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class MepcConfig {
	@Inject
	public MepcConfig(@Named("logback_config") String logbackConfig) {
	}
}

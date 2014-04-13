package net.mepc.modules.logger;

import org.slf4j.Logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;

public class SLF4JTypeListener implements TypeListener {
	public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
		for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
			if (field.getType() == Logger.class) {
				typeEncounter.register(new SLF4JMembersInjector<T>(field));
			}
		}
	}
}
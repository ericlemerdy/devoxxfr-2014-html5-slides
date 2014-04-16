package net.mepc.repository.db;

public class SystemEnvironmentProvider {

    public String get(String name) {
        return System.getenv(name);
    }
}

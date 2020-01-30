package net.avdw.mail;

import net.avdw.liquibase.JdbcUrl;

public final class PropertyKey {
    public static final String LOGGING_LEVEL = String.format("%s.level", Logging.class.getCanonicalName());
    public static final String RELEASE_MODE = String.format("%s.release", Main.class.getCanonicalName());
    public static final String DATABASE_PATH = String.format("%s.path", JdbcUrl.class.getCanonicalName());

    private PropertyKey() {
    }
}

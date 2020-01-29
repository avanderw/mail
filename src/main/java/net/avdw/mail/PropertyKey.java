package net.avdw.mail;

public final class PropertyKey {
    public static final String LOGGING_LEVEL = String.format("%s.level", Logging.class.getCanonicalName());
    public static final String RELEASE_MODE = String.format("%s.release", Main.class.getCanonicalName());

    private PropertyKey() {
    }
}

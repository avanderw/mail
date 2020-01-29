package net.avdw;

import com.google.inject.Singleton;

import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class Runtime {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    private final long startedMs;

    public Runtime() {
        startedMs = System.currentTimeMillis();
    }

    public String getStarted() {
        return SIMPLE_DATE_FORMAT.format(new Date(startedMs));
    }

    public String getDuration() {
        return String.format("%,dms", System.currentTimeMillis() - startedMs);
    }
}

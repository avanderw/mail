package net.avdw.property;

import com.google.inject.Inject;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileWriter {
    private final Properties defaultProperties;

    @Inject
    PropertyFileWriter(@DefaultProperty final Properties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    public void write(final File file) {
        try {
            defaultProperties.store(new FileWriter(file), "net.avdw.mail cli properties");
            Logger.info("Wrote property file [{}]", file.getAbsolutePath());
        } catch (IOException e) {
            Logger.error(e.getMessage());
            Logger.debug(e);
        }
    }
}

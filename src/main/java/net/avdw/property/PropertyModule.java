package net.avdw.property;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.pmw.tinylog.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class PropertyModule extends AbstractModule {
    @Provides
    @Singleton
    @EnvironmentProperty
    Properties environmentProperties() {
        Properties properties = new Properties();
        properties.putAll(System.getenv());
        return properties;
    }

    @Provides
    @Singleton
    @SystemProperty
    Properties systemProperties() {
        return System.getProperties();
    }

    @Provides
    @Singleton
    @GlobalProperty
    Properties globalProperties(final @GlobalProperty Path globalPropertyPath) {
        return loadProperties(globalPropertyPath);
    }

    @Provides
    @Singleton
    @LocalProperty
    Properties localProperties(final @LocalProperty Path localPropertyPath) {
        return loadProperties(localPropertyPath);
    }

    private Properties loadProperties(final Path propertyPath) {
        Properties properties = new Properties();
        if (Files.exists(propertyPath)) {
            try {
                properties.load(new FileReader(propertyPath.toFile()));
                Logger.debug(String.format("Loaded property file: %s", propertyPath));
            } catch (IOException e) {
                Logger.error("Could not load property file");
            }
        } else {
            Logger.debug("Property path does not exist [{}]", propertyPath);
        }
        return properties;
    }
}

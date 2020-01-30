package net.avdw.mail;

import com.google.inject.*;
import net.avdw.ProfilingModule;
import net.avdw.Runtime;
import net.avdw.liquibase.DbConnection;
import net.avdw.liquibase.JdbcUrl;
import net.avdw.mail.cli.MainCli;
import net.avdw.property.*;
import org.pmw.tinylog.Logger;
import picocli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public final class Main {
    private Main() {
    }

    public static void main(final String[] args) {
        CommandLine commandLine = new CommandLine(MainCli.class, new GuiceFactory());
        commandLine.execute(args);
    }

    public static final class GuiceFactory implements CommandLine.IFactory {
        private static final Injector INJECTOR = Guice.createInjector(new GuiceModule());

        @Override
        public <K> K create(final Class<K> aClass) {
            return INJECTOR.getInstance(aClass);
        }

        static class GuiceModule extends AbstractModule {
            @Override
            protected void configure() {
                bind(Runtime.class).toInstance(new Runtime());
                bind(List.class).to(LinkedList.class);

                bind(Path.class).annotatedWith(GlobalProperty.class).toInstance(Paths.get(System.getProperty("user.home")).resolve("net.avdw/mail.properties"));
                bind(Path.class).annotatedWith(LocalProperty.class).toInstance(Paths.get("").resolve("mail.properties"));

                install(new PropertyModule());
                bind(Logging.class).asEagerSingleton();
                install(new ProfilingModule("net.avdw.mail", ""));

                bind(SimpleDateFormat.class).toInstance(new SimpleDateFormat("yyyy-MM-dd"));
            }

            @Provides
            @Singleton
            @DefaultProperty
            Properties defaultProperties() {
                Properties properties = new Properties();
                properties.put(PropertyKey.LOGGING_LEVEL, "INFO");
                properties.put(PropertyKey.RELEASE_MODE, "false");
                properties.put(PropertyKey.DATABASE_PATH, "mail.sqlite");
                return properties;
            }

            @Provides
            @Singleton
            @JdbcUrl
            String jdbcUrl(final PropertyResolver propertyResolver) {
                return String.format("jdbc:sqlite:%s", propertyResolver.resolve(PropertyKey.DATABASE_PATH));
            }

            @Provides
            @Singleton
            @DbConnection
            Connection dbConnection(@JdbcUrl final String jdbcUrl) {
                try {
                    return DriverManager.getConnection(jdbcUrl);
                } catch (SQLException e) {
                    Logger.error(e.getMessage());
                    Logger.debug(e);
                    throw new UnsupportedOperationException();
                }
            }
        }
    }
}

package net.avdw.mail;

import com.google.inject.*;
import net.avdw.ProfilingModule;
import net.avdw.Runtime;
import net.avdw.liquibase.JdbcUrl;
import net.avdw.liquibase.LiquibaseRunner;
import net.avdw.property.DefaultProperty;
import net.avdw.property.GlobalProperty;
import net.avdw.property.LocalProperty;
import net.avdw.property.PropertyModule;
import net.avdw.mail.cli.MainCli;
import picocli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public final class Main {
    private Main() {
    }

    public static void main(final String[] args) {
        LiquibaseRunner liquibaseRunner = GuiceFactory.INJECTOR.getInstance(LiquibaseRunner.class);
        liquibaseRunner.update();

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
                bind(String.class).annotatedWith(JdbcUrl.class).toInstance("jdbc:sqlite:mail.sqlite");
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
                return properties;
            }
        }
    }
}

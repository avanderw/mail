package net.avdw.mail.cli;

import com.google.inject.Inject;
import net.avdw.liquibase.LiquibaseRunner;
import org.pmw.tinylog.Logger;
import picocli.CommandLine;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(name = "mail", description = "The dynamic address book", version = "1.0-SNAPSHOT", mixinStandardHelpOptions = true,
subcommands = {ConfigCli.class})
public class MainCli implements Runnable {
    @CommandLine.Parameters(description = "One or more filters to apply")
    private List<String> filters = new ArrayList<>();

    @Inject
    private LiquibaseRunner liquibaseRunner;

    /**
     * Entry point for picocli.
     */
    @Override
    public void run() {
        liquibaseRunner.update();
        filters.forEach(Logger::debug);
        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    URI mailto = new URI("mailto:avanderw@gmail.com?subject=Test");
                    desktop.mail(mailto);
                } catch (IOException | URISyntaxException e) {
                    Logger.error(e.getMessage());
                    Logger.debug(e);
                }
            } else {
                throw new UnsupportedOperationException("Alternative launch method not supported");
            }
        } else {
            throw new UnsupportedOperationException("Alternative launch method not supported");
        }
    }
}

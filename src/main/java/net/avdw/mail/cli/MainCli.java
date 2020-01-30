package net.avdw.mail.cli;

import com.google.inject.Inject;
import net.avdw.liquibase.LiquibaseRunner;
import net.avdw.mail.db.PersonTable;
import net.avdw.mail.db.PersonTableQuery;
import org.pmw.tinylog.Logger;
import picocli.CommandLine;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CommandLine.Command(name = "mail", description = "The dynamic address book", version = "1.0-SNAPSHOT", mixinStandardHelpOptions = true,
subcommands = {ConfigCli.class})
public class MainCli implements Runnable {
    @CommandLine.Parameters(description = "One or more filters to apply")
    private List<String> filters = new ArrayList<>();

    @Inject
    private LiquibaseRunner liquibaseRunner;
    @Inject
    private PersonTableQuery personTableQuery;

    /**
     * Entry point for picocli.
     */
    @Override
    public void run() {
        liquibaseRunner.update();

        List<PersonTable> personTableList;
        if (filters.isEmpty()) {
            personTableList = personTableQuery.queryAll();
        } else {
            throw new UnsupportedOperationException();
        }

        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                try {
                    String emails = personTableList.stream().map(PersonTable::getEmail).collect(Collectors.joining(";"));
                    String mailto = String.format("mailto:%s?subject=Test", emails);
                    URI mailUri = new URI(mailto);
                    desktop.mail(mailUri);
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

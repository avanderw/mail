package net.avdw.liquibase;

import com.google.inject.Inject;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.pmw.tinylog.Logger;

import java.sql.Connection;

public class LiquibaseRunner {
    private final Connection connection;

    @Inject
    LiquibaseRunner(@DbConnection final Connection connection) {
        this.connection = connection;
    }
    public void update() {
        Liquibase liquibase = null;
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase = new Liquibase("net/avdw/liquibase/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("main");
        } catch (LiquibaseException e) {
            Logger.error(e.getMessage());
            Logger.debug(e);
        }

    }
}

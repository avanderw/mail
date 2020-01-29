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
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseRunner {
    private final String jdbcUrl;

    @Inject
    LiquibaseRunner(@JdbcUrl final String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void update() {
        Liquibase liquibase = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase = new Liquibase("net/avdw/liquibase/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("main");
        } catch (SQLException | LiquibaseException e) {
            Logger.error(e.getMessage());
            Logger.debug(e);
        }

    }
}

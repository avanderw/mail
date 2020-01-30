package net.avdw.mail.db;

import com.google.inject.Inject;
import net.avdw.liquibase.DbConnection;
import org.pmw.tinylog.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonTableQuery {
    private final String allQuery = "SELECT * FROM Person WHERE Active = 'YES'";
    private final Connection connection;

    @Inject
    PersonTableQuery(@DbConnection final Connection connection) {
        this.connection = connection;
    }

    public List<PersonTable> queryAll() {
        return query(allQuery);
    }

    public List<PersonTable> queryRoles(final List<String> filters) {
        String filter = filters.stream().map(s -> String.format("Role LIKE '%%%s%%'", s)).collect(Collectors.joining(" OR "));
        String filterQuery = String.format("%s AND (%s)", allQuery, filter);
        return query(filterQuery);
    }

    private List<PersonTable> query(final String sql) {
        Logger.debug("Query: {}", sql);
        List<PersonTable> personTableList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                personTableList.add(new PersonTable(resultSet));
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage());
            Logger.debug(e);
        }
        return personTableList;
    }
}

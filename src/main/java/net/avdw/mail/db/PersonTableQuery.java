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

public class PersonTableQuery {
    private Connection connection;

    @Inject
    PersonTableQuery(@DbConnection final Connection connection) {
        this.connection = connection;
    }

    public List<PersonTable> queryAll() {
        String sql = "SELECT * FROM Person WHERE Active = 'YES'";
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

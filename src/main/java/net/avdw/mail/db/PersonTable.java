package net.avdw.mail.db;

import org.pmw.tinylog.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonTable {
    private Integer pk;
    private String name;
    private Boolean active;
    private String role;
    private String email;

    public PersonTable(final ResultSet resultSet) {
        try {
            pk = resultSet.getInt("PK");
            name = resultSet.getString("Name");
            active = resultSet.getString("Active").equals("YES");
            role = resultSet.getString("Role");
            email = resultSet.getString("Email");
        } catch (SQLException e) {
            Logger.error(e.getMessage());
            Logger.debug(e);
        }
    }

    public Integer getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pk=" + pk +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

package com.learnup.test.jbdc;

import com.learnup.test.jbdc.entities.Day;
import java.sql.*;

public class DbHelper {
    private Connection connection;

    public DbHelper(String dbUrl, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean addDay(Day day) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO manager(day, steps) VALUES(?, ?);");
            statement.setInt(1, day.getDay());
            statement.setInt(2, day.getSteps());
            int modifyCount = statement.executeUpdate(); //кол-во измененных строк
            return modifyCount > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }

    public boolean updateDay(Day day) {
        int steps = getStepsByDay(day.getDay());
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE manager SET steps = ? WHERE day = ?;");
            statement.setInt(1, steps + day.getSteps());
            statement.setInt(2, day.getDay());
            int modifyCount = statement.executeUpdate();
            return modifyCount > 0;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public Integer getStepsByDay(Integer day) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM manager WHERE day = ?;");
            statement.setInt(1, day);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("steps");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public boolean deleteDay(Integer day){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM manager WHERE day = ?;");
            statement.setInt(1, day);
            int modifyCount = statement.executeUpdate();
            return modifyCount > 0;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }
}

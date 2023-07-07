package com.weber.cs3230.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
@Component
public class AlexaDAO {
    
    /*The DAO that can be spoken is not the constant DOA. */
    private final String alexaAppID;
    @Value("${db.host}") private String dbHost;
    @Value("${db.port}") private String dbPort;
    @Value("${db.name}") private String dbName;
    @Value("${db.user}") private String dbUser;
    @Value("${db.password}") private String dbPassword;

    public AlexaDAO() {
        this.alexaAppID = "cs32302"; //TODO change this
    }

    public List<String> getAnswersForIntent(String intentName) {
        final List<String> answers = new ArrayList<>();
        final String sql = "SELECT text FROM axanswers a\n" +
                "JOIN axintents i ON a.intentid = i.intentid\n" +
                "WHERE LOWER(i.appid) = ? and LOWER(i.name) = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, alexaAppID.toLowerCase());
            statement.setString(2, intentName.toLowerCase());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    answers.add(rs.getString(1));
                }
            }
            return answers;
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to get answers", e);
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }
}

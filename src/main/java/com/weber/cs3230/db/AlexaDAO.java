package com.weber.cs3230.db;

import com.weber.cs3230.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
@Component
public class AlexaDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String alexaAppID;
    @Value("${db.host}") private String dbHost;
    @Value("${db.port}") private String dbPort;
    @Value("${db.name}") private String dbName;
    @Value("${db.user}") private String dbUser;
    @Value("${db.password}") private String dbPassword;

    public AlexaDAO() {
        this.alexaAppID = "yourPersonalAlexaAppID"; //TODO set this back to what it was
    }

    public List<String> getAnswersForIntent(String intentName) {
        log.info("Getting answers for " + intentName);

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

    public boolean validateCreds(Credentials credentials) {
        log.info("Validating creds...");

        final String sql = "SELECT username, password FROM axprincipal WHERE LOWER(appid) = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, alexaAppID.toLowerCase());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    final String username = rs.getString("username");
                    final String password = rs.getString("password");
                    return username.equals(credentials.getUsername()) && password.equals(credentials.getPassword());
                }
            }
            return false;
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to validate creds", e);
        }
    }

    public IntentDetailList getIntentList() {
        log.info("Getting full intent list");

        final IntentDetailList detailList = new IntentDetailList();
        final String sql = "SELECT intentid, name, dtstamp FROM axintents WHERE LOWER(appid) = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, alexaAppID.toLowerCase());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    final IntentDetail intent = new IntentDetail();
                    intent.setIntentID(rs.getLong("intentid"));
                    intent.setName(rs.getString("name"));
                    intent.setDateAdded(parseDBDate(rs.getTimestamp("dtstamp")));
                    detailList.getIntents().add(intent);
                }
            }
            return detailList;
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to get intent list", e);
        }
    }

    public IntentAnswerList getAnswerList(long intentID) {
        log.info("Getting answers for intent " + intentID);

        final IntentAnswerList answerList = new IntentAnswerList();
        final String sql = "SELECT answerid, text, intentid, dtstamp FROM axanswers WHERE intentid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, intentID);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    final IntentAnswer answer = new IntentAnswer();
                    answer.setAnswerID(rs.getLong("answerid"));
                    answer.setText(rs.getString("text"));
                    answer.setIntentID(rs.getLong("intentid"));
                    answer.setDateAdded(parseDBDate(rs.getTimestamp("dtstamp")));
                    answerList.getAnswers().add(answer);
                }
            }
            return answerList;
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to get intent list", e);
        }
    }

    public IntentDetail saveNewIntent(IntentDetail intent) {
        log.info("Saving new intent " + intent.getName());

        final String sql = "INSERT INTO axintents (name, appid, dtstamp) VALUES (?, ?, now())";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, intent.getName());
            statement.setString(2, alexaAppID.toLowerCase());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                intent.setIntentID(rs.getInt(1));
            }
            return getIntent(intent.getIntentID());
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to save new intent", e);
        }
    }

    public IntentDetail saveExistingIntent(IntentDetail intent) {
        log.info("Saving existing intent " + intent.getName() + " " + intent.getIntentID());

        final String sql = "UPDATE axintents SET name = ? WHERE intentid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, intent.getName());
            statement.setLong(2, intent.getIntentID());
            statement.executeUpdate();
            return getIntent(intent.getIntentID());
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to save existing intent", e);
        }
    }

    private IntentDetail getIntent(long intentID) {
        log.info("Getting intent " + intentID);

        final String sql = "SELECT intentid, name, dtstamp FROM axintents WHERE intentid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, intentID);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    final IntentDetail intent = new IntentDetail();
                    intent.setIntentID(rs.getLong("intentid"));
                    intent.setName(rs.getString("name"));
                    intent.setDateAdded(parseDBDate(rs.getTimestamp("dtstamp")));
                    return intent;
                }
            }
            return null;
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to get intent " + intentID, e);
        }
    }

    public void deleteIntent(long intentID) {
        deleteAllAnswersForIntent(intentID);

        log.info("Deleting intent " + intentID);
        final String sql = "DELETE FROM axintents WHERE intentid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, intentID);
            statement.executeUpdate();
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to delete intent " + intentID, e);
        }
    }

    public IntentAnswer saveNewAnswer(IntentAnswer answer, long intentID) {
        log.info("Saving new answer for intent " + intentID);

        final String sql = "INSERT INTO axanswers (text, intentid, dtstamp) VALUES (?, ?, now())";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, answer.getText());
            statement.setLong(2, intentID);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                answer.setAnswerID(rs.getInt(1));
            }
            return getAnswer(answer.getAnswerID());
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to save new answer", e);
        }
    }

    public IntentAnswer saveExistingAnswer(IntentAnswer answer, long intentID) {
        log.info("Saving existing answer for intent " + intentID);
        validateAnswer(intentID, answer.getAnswerID());

        final String sql = "UPDATE axanswers SET text = ? WHERE answerid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, answer.getText());
            statement.setLong(2, answer.getAnswerID());
            statement.executeUpdate();
            return getAnswer(answer.getAnswerID());
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to save existing answer", e);
        }
    }

    private IntentAnswer getAnswer(long answerID) {
        log.info("Getting answer " + answerID);

        final String sql = "SELECT answerid, text, intentid, dtstamp FROM axanswers WHERE answerid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, answerID);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    final IntentAnswer answer = new IntentAnswer();
                    answer.setAnswerID(rs.getLong("answerid"));
                    answer.setText(rs.getString("text"));
                    answer.setIntentID(rs.getLong("intentid"));
                    answer.setDateAdded(parseDBDate(rs.getTimestamp("dtstamp")));
                    return answer;
                }
            }
            return null;
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to get answer " + answerID, e);
        }
    }

    private void deleteAllAnswersForIntent(long intentID) {
        log.info("Deleting all answers for intent " + intentID);

        final String sql = "DELETE FROM axanswers WHERE intentid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, intentID);
            statement.executeUpdate();
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to delete all answers for intent " + intentID, e);
        }
    }

    public void deleteAnswer(long intentID, long answerID) {
        log.info("Deleting answer " + answerID);
        validateAnswer(intentID, answerID);

        final String sql = "DELETE FROM axanswers WHERE answerid = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, answerID);
            statement.executeUpdate();
        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException("Failed to delete answer " + answerID, e);
        }
    }

    private void validateAnswer(long intentID, long answerID) {
        final IntentAnswer existingAnswer = getAnswer(answerID);
        if (existingAnswer == null) {
            throw new RuntimeException("Failed to find answer by id " + answerID);
        } else if (existingAnswer.getIntentID() != intentID) {
            throw new RuntimeException("Intent/answer mismatch (answer " + answerID + " does not belong to intent " + intentID + ")");
        }
    }

    private String parseDBDate(Timestamp dbDate) {
        return new SimpleDateFormat("MM/dd/yyyy").format(dbDate);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }
}

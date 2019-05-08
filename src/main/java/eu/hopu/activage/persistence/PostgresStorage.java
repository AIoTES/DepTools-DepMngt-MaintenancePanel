package eu.hopu.activage.persistence;

import eu.hopu.activage.services.dto.Record;
import eu.hopu.activage.services.dto.RecordStatus;
import eu.hopu.activage.services.dto.RecordType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class PostgresStorage implements RecordStorage {

    private static final Logger LOG = LogManager.getLogger(PostgresStorage.class);

    private String username;
    private String password;
    private String databaseUrl;

    public PostgresStorage(String localhost, String port, String database, String username, String password) {
        this.databaseUrl = "jdbc:postgresql://" + localhost + ":" + port + "/" + database;
        this.username = username;
        this.password = password;
        connectToDatabase(username, password);

    }

    private void connectToDatabase(String username, String password) {
        try {
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
                LOG.info("Database connection established");
                createTable(connection);
            } catch (SQLException e) {
                LOG.warn("Cannot connect to database. Waiting for database to connect...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                connectToDatabase(username, password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void createTable(Connection connection) {
        try {
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS records" +
                            " (id SERIAL PRIMARY KEY," +
                            "elementId TEXT," +
                            "description TEXT," +
                            "status TEXT," +
                            "type TEXT," +
                            "timestamp BIGINT )"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Record createRecord(Record record) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String statement = String.format(
                    "INSERT INTO records(elementId, description, status, type, timestamp)" +
                            " VALUES('%s', '%s', '%s', '%s', %d)", record.getElementId(),
                    record.getDescription(), record.getRecordStatus().getStatus(),
                    record.getRecordType().getType(), record.getTimestamp().getTime()
            );

            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                LOG.info("{} record inserted", record.toString());
                return record;
            } else {
                LOG.info("{} record not inserted", record.toString());
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot insert → " + record.toString());
            return null;
        }
    }

    @Override
    public Record updateRecord(Record record) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String statement = String.format(
                    "UPDATE records " +
                            "SET description='%s', status='%s', type='%s', timestamp=%d " +
                            "WHERE id = '%d' AND elementId = '%s'", record.getDescription(), record.getRecordStatus().getStatus(),
                    record.getRecordType().getType(), record.getTimestamp().getTime(), record.getId(), record.getElementId()
            );

            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                LOG.info("{} record updated", record.toString());
                return record;
            } else {
                LOG.info("{} record not updated", record.toString());
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot update → " + record.toString());
            return null;
        }
    }

    @Override
    public List<Record> getRecordsByElementId(String elementId) {
        List<Record> records = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String query = String.format("SELECT * FROM records WHERE elementId = '%s'", elementId);

            Statement preparedStatement = connection.createStatement();

            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                records.add(new Record(resultSet.getLong("id"),
                        resultSet.getString("elementId"),
                        resultSet.getString("description"),
                        RecordStatus.valueOf(resultSet.getString("status").toUpperCase()),
                        RecordType.valueOf(resultSet.getString("type").toUpperCase()),
                        resultSet.getLong("timestamp")));
            }

            return records;

        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot retrieve data from elementId → " + elementId);
            return records;
        }
    }

    @Override
    public boolean deleteRecordById(String elementId, long id) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String statement = String.format("DELETE FROM records WHERE id = '%d' AND elementId = '%s'", id, elementId);

            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                LOG.info("{}:{} record deleted", elementId, id);
                return true;
            } else {
                LOG.info("{}:{} record not deleted", elementId, id);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot delete → {}:{}", elementId, id);
            return false;
        }
    }

}

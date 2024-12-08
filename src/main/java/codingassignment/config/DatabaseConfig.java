package codingassignment.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

    // MySQL Connection
    public static Connection getMySQLConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/assignment";
        String user = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    // MongoDB Connection
    public static MongoDatabase getMongoConnection() {
        String mongoUri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(mongoUri);
        return mongoClient.getDatabase("assignment");
    }
}

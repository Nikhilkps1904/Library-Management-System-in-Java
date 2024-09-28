import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "library";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static void connect() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            connect();
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}
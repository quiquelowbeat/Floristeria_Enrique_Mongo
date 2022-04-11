package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBConnection {

    private static DBConnection instance;
    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    public static final MongoDatabase db = mongoClient.getDatabase("floristeria_enrique");

    private DBConnection() {} // Patrón Singleton para la conexión a la DB.

    public static void getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
    }

}

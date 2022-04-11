package repositories;

import com.mongodb.client.MongoCollection;
import entities.Flower;
import entities.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.db;

public class FlowerRepository {

    private final MongoCollection<Document> flowersMongo;
    private List<Document> query;

    public FlowerRepository() {
        this.flowersMongo = db.getCollection("flowers");
    }

    public List<Document> sentQueryToMongo(){
        return flowersMongo.find().into(new ArrayList<>());
    }

    public Flower createFlower(String name, String color, double price, int quantity){
        return new Flower(name, color, price, quantity);
    }

    public boolean addFlower(Flower flower){
        Document queryNameColor = flowersMongo.find(new Document("name", flower.getName()).append("color", flower.getColor())).first();

        if(queryNameColor != null && flower.getName().equalsIgnoreCase(queryNameColor.getString("name")) &&
                flower.getColor().equalsIgnoreCase(queryNameColor.getString("color"))){
            int newQuantity = flower.getQuantity() + queryNameColor.getInteger("quantity");
            Document newDocument = new Document("quantity", newQuantity);
            Document updateDocument = new Document("$se", newDocument);
            flowersMongo.updateOne(queryNameColor, updateDocument);
        } else {
            Document flowerDB = new Document();
            flowerDB.put("name", flower.getName());
            flowerDB.put("color", flower.getColor());
            flowerDB.put("price", flower.getPrice());
            flowerDB.put("quantity", flower.getQuantity());
            flowersMongo.insertOne(flowerDB);
        }

        return true;
    }

    public MongoCollection<Document> getFlowersMongo() {
        return flowersMongo;
    }

    public int removeFlower(String name, String color, int quantity){
        query = sentQueryToMongo();
        boolean exist = false;
        int option = 0;

        Document document = findByNameAndColor(name, color);

        try{
            if(document.getInteger("quantity") >= quantity){
                int newQuantity = document.getInteger("quantity") - quantity;
                Document newDocument = new Document("quantity", newQuantity);
                Document updateObject = new Document("$set", newDocument);
                flowersMongo.updateOne(document, updateObject);
                option = 1;
            } else if(document.getInteger("quantity") < quantity){
                option = 2;
            }
        } catch (NullPointerException e) {

        }

        return option;
    }
    public Document findByNameAndColor(String name, String color){
        query = sentQueryToMongo();
        Document document = null;
        boolean exist = false;
        int i = 0;

        while(!exist && i < query.size()){
            if(name.equalsIgnoreCase(query.get(i).getString("name")) &&
                    color.equalsIgnoreCase(query.get(i).getString("color"))){
                exist = true;
                document = query.get(i);
            }
            i++;
        }
        return document;
    }

    public int getFlowerStockQuantity(){
        query = sentQueryToMongo();
        int quantity = 0;
        for(Document doc: query){
            quantity += doc.getInteger("quantity");
        }
        return quantity;
    }

    public List<Product> getFlowersFromDatabase(){
        query = sentQueryToMongo();
        List<Product> flowers = new ArrayList<>();
        query.forEach(document -> {
            Flower flower = new Flower(document.getString("name"), document.getString("color"),
                    document.getDouble("price"), document.getInteger("quantity"));
            flowers.add(flower);
        });
        return flowers;
    }

    public List<Document> getArrayFlowers(){
        return sentQueryToMongo();
    }
    public int getFlowerQuantity(int i){
        query = sentQueryToMongo();
        return query.get(i).getInteger("quantity");
    }
    public double getFlowerPrice(int i){
        query = sentQueryToMongo();
        return query.get(i).getDouble("price");
    }

    /*
    public Product findOne (String name){
        Product product = null;
        boolean exist = false;
        int i = 0;

        while (!exist && i< database.getFlowers().size()) {

            if (name.equalsIgnoreCase(database.getFlowers().get(i).getName())) {

                exist = true;
                product = database.getFlowers().get(i);

            }
            i++;
        }
        return product;
    }
    */

}

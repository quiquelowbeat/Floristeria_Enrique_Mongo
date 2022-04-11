package repositories;

import com.mongodb.client.MongoCollection;
import entities.Decor;
import entities.Product;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.db;

public class DecorRepository {

    private MongoCollection<Document> decorMongo;
    private List<Document> query;

    public DecorRepository(){

        this.decorMongo = db.getCollection("decor");;
    }

    public List<Document> sentQueryToMongo(){
        return decorMongo.find().into(new ArrayList<>());
    }

    public Decor createDecor(String name, double price, int quantity){
        return new Decor(name, price, quantity);
    }

    public boolean addDecor(Decor decor){
        Document queryNameMaterial = decorMongo.find(new Document("name", decor.getName()).append("material", decor.getTypeOfMaterial())).first();

        if (queryNameMaterial != null && queryNameMaterial.getString("name").equalsIgnoreCase(decor.getName())
                && queryNameMaterial.getString("material").equalsIgnoreCase(decor.getTypeOfMaterial())) {
            int newQuantity = decor.getQuantity() + queryNameMaterial.getInteger("quantity");
            Document newDocument = new Document("quantity", newQuantity);
            Document updateDocument = new Document("$se", newDocument);
            decorMongo.updateOne(queryNameMaterial, updateDocument);
        } else {
            Document decorDB = new Document();
            decorDB.put("name", decor.getName());
            decorDB.put("material", decor.getTypeOfMaterial());
            decorDB.put("price", decor.getPrice());
            decorDB.put("quantity", decor.getQuantity());
            decorMongo.insertOne(decorDB);
        }
        return true;
    }

    public MongoCollection<Document> getDecorMongo() {
        return decorMongo;
    }

    public int removeDecor(String name, String material, int quantity){
        query = sentQueryToMongo();
        boolean exist = false;
        int option = 0;

        Document document = findByNameAndMaterial(name, material);

        try{
            if(document.getInteger("quantity") >= quantity){
                int newQuantity = document.getInteger("quantity") - quantity;
                Document newDocument = new Document("quantity", newQuantity);
                Document updateObject = new Document("$set", newDocument);
                decorMongo.updateOne(document, updateObject);
                option = 1;
            } else if(document.getInteger("quantity") < quantity){
                option = 2;
            }
        } catch (NullPointerException e){

        }

        return option;
    }

    public Document findByNameAndMaterial(String name, String material){
        query = sentQueryToMongo();
        Document document = null;
        boolean exist = false;
        int i = 0;

        while(!exist && i < query.size()){
            if(name.equalsIgnoreCase(query.get(i).getString("name")) &&
                    material.equalsIgnoreCase(query.get(i).getString("material"))){
                exist = true;
                document = query.get(i);
            }
            i++;
        }
        return document;
    }

    public int getDecorStockQuantity(){
        query = sentQueryToMongo();
        int quantity = 0;
        for(Document doc: query){
            quantity += doc.getInteger("quantity");
        }
        return quantity;
    }


    public List<Product> getDecorFromDatabase(){
        query = sentQueryToMongo();
        List<Product> decorations = new ArrayList<>();
        query.forEach(document -> {
            Decor decor = new Decor(document.getString("name"), document.getDouble("price"),
                    document.getInteger("quantity"));
            decor.setTypeOfMaterial(document.getString("material"));
            decorations.add(decor);
        });
        return decorations;
    }

    public List<Document> getArrayDecor(){
        return sentQueryToMongo();
    }
    public int getDecorQuantity(int i){
        query = sentQueryToMongo();
        return query.get(i).getInteger("quantity");
    }
    public double getDecorPrice(int i){
        query = sentQueryToMongo();
        return query.get(i).getDouble("price");
    }

    /*
    public Product findOne (String name){
        Product product = null;
        boolean exist = false;
        int i = 0;

        while (!exist && i< database.getDecorations().size()) {

            if (name.equalsIgnoreCase(database.getDecorations().get(i).getName())) {

                exist = true;
                product = database.getDecorations().get(i);

            }
            i++;
        }
        return product;
    }
    */
}

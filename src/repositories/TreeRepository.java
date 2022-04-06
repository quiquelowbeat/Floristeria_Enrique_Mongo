package repositories;

import com.mongodb.client.MongoCollection;
import entities.Product;
import entities.Tree;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.db;

public class TreeRepository {

    private final MongoCollection<Document> treesMongo;
    private List<Document> query;

    public TreeRepository() {
        this.treesMongo = db.getCollection("trees");
    }

    public List<Document> sentQueryToMongo(){
        return treesMongo.find().into(new ArrayList<>());
    }

    public Tree createTree(String name, double price, int quantity) {
        return new Tree(name, price, quantity);
    }

    public boolean addTree(Tree tree) {
        Document queryNameSize = treesMongo.find(new Document("name", tree.getName()).append("size", tree.getSize())).first();

        if (queryNameSize != null && queryNameSize.getString("name").equalsIgnoreCase(tree.getName())
                && queryNameSize.getString("size").equalsIgnoreCase(tree.getSize())) {
            int newQuantity = tree.getQuantity() + queryNameSize.getInteger("quantity");
            Document newDocument = new Document("quantity", newQuantity);
            Document updateObject = new Document("$set", newDocument);
            treesMongo.updateOne(queryNameSize, updateObject);
        } else {
            Document treeDB = new Document();
            treeDB.put("name", tree.getName());
            treeDB.put("size", tree.getSize());
            treeDB.put("price", tree.getPrice());
            treeDB.put("quantity", tree.getQuantity());
            treesMongo.insertOne(treeDB);
        }

        return true;
    }

    public int removeTree(String name, String size, int quantity){
        query = sentQueryToMongo();
        boolean exist = false;
        int option = 0;
        int i = 0;

        while(!exist && i < query.size()){
            if(name.equalsIgnoreCase(query.get(i).getString("name")) &&
                    size.equalsIgnoreCase(query.get(i).getString("size"))){
                exist = true;
                if(query.get(i).getInteger("quantity") >= quantity){
                    int newQuantity = query.get(i).getInteger("quantity") - quantity;
                    Document newDocument = new Document("quantity", newQuantity);
                    Document updateObject = new Document("$set", newDocument);
                    treesMongo.updateOne(query.get(i), updateObject);
                    option = 1;
                } else if(query.get(i).getInteger("quantity") < quantity){
                    option = 2;
                }
            }
            i++;
        }
        return option;
    }

    public int getTreeStockQuantity(){
        query = sentQueryToMongo();
        int quantity = 0;
        for(Document doc: query){
            quantity += doc.getInteger("quantity");
        }
        return quantity;
    }

    public List<Product> getTreesFromDatabase(){
        query = sentQueryToMongo();
        List<Product> trees = new ArrayList<>();
        query.forEach(document -> {
            Tree tree = new Tree(document.getString("name"), document.getDouble("price"),
                    document.getInteger("quantity"));
            tree.setSizeString(document.getString("size"));
            trees.add(tree);
        });
        return trees;
    }

    public List<Document> getArrayTrees(){
        return sentQueryToMongo();
    }
    public int getTreeQuantity(int i){
        query = sentQueryToMongo();
        return query.get(i).getInteger("quantity");
    }
    public double getTreePrice(int i){
        query = sentQueryToMongo();
        return query.get(i).getDouble("price");
    }

    public Document findByNameAndSize(String name, String size){
        query = sentQueryToMongo();
        Document document = null;
        boolean exist = false;
        int i = 0;

        while(!exist && i < query.size()){
            if(name.equalsIgnoreCase(query.get(i).getString("name")) &&
                    size.equalsIgnoreCase(query.get(i).getString("size"))){
                exist = true;
                document = query.get(i);
            }
            i++;
        }
        return document;
    }
     /*
    public Product findOne (String name){
        Product product = null;
        boolean exist = false;
        int i = 0;

        while (!exist && i< database.getTrees().size()) {

            if (name.equalsIgnoreCase(database.getTrees().get(i).getName())) {

                exist = true;
                product = database.getTrees().get(i);

            }
            i++;
        }
        return product;
    }
    */
}


/*
    public static void Test() {

        List<Document> listElementsMongo = treesMongo.find().into(new ArrayList<>());
        for (Document doc : listElementsMongo) {
            System.out.println(doc.toJson());
        }

        // De esta manera podemos tirar todos los datos de golpe al crear el Document.
        // Document treeDB = new Document("name", Keyboard.readString("Insert name:")).append("height", Keyboard.readInt("Insert height:")).append("price", Keyboard.readInt("Price:"));
        Document treeDB = new Document();
        treeDB.put("name", Keyboard.readString("Insert name:"));
        treeDB.put("height", Keyboard.readInt("Insert height:"));
        treeDB.put("price", Keyboard.readInt("Price:"));
        Document product = treesMongo.find(new Document("name", "olivo")).first(); // Meter objeto olivo dentro de otro.
        treeDB.put("products", product);
        treesMongo.insertOne(treeDB);

        listElementsMongo = treesMongo.find().into(new ArrayList<>());
        for (Document doc : listElementsMongo) {
            System.out.println("Name: " + doc.get("name") + " - Height: " + doc.get("height") + " - Price: " + doc.get("price") + "€");
        }
        Document query = treesMongo.find(new Document("name", "olivo")).first();
        System.out.println(query);

        // Buscar usando cursor.

        Document query2 = new Document("height", 3);
        FindIterable<Document> cursor = treesMongo.find(query2);
        System.out.println(cursor);

        /*

        Document query = new Document("name", "olivo");
        MongoCursor<Document> cursor = treesMongo.find(query).cursor();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.println(doc);
        }

        MongoCursor<Document> cursor = treesMongo.find().cursor();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.println(doc);
        }
        */

// Consulta
// Document document = treesMongo.find().first(); // Primer árbol de la lista
// System.out.println(document.toJson());



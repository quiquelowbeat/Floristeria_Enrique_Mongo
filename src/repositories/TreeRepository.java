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

    public List<Document> sentQueryToMongo() {
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

    public MongoCollection<Document> getTreesMongo() {
        return treesMongo;
    }

    public int removeTree(String name, String size, int quantity) {
        query = sentQueryToMongo();
        boolean exist = false;
        int option = 0;

        Document document = findByNameAndSize(name, size);

        try{
            if (document.getInteger("quantity") >= quantity) {
                int newQuantity = document.getInteger("quantity") - quantity;
                Document newDocument = new Document("quantity", newQuantity);
                Document updateObject = new Document("$set", newDocument);
                treesMongo.updateOne(document, updateObject);
                option = 1;
            } else if (document.getInteger("quantity") < quantity) {
                option = 2;
            }
        } catch (NullPointerException e){

        }

        return option;
    }

    public Document findByNameAndSize(String name, String size) {
        query = sentQueryToMongo();
        Document document = null;
        boolean exist = false;
        int i = 0;

        while (!exist && i < query.size()) {
            if (name.equalsIgnoreCase(query.get(i).getString("name")) &&
                    size.equalsIgnoreCase(query.get(i).getString("size"))) {
                exist = true;
                document = query.get(i);
            }
            i++;
        }
        return document;
    }

    public int getTreeStockQuantity() {
        query = sentQueryToMongo();
        int quantity = 0;
        for (Document doc : query) {
            quantity += doc.getInteger("quantity");
        }
        return quantity;
    }

    public List<Product> getTreesFromDatabase() {
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

    public List<Document> getArrayTrees() {
        return sentQueryToMongo();
    }

    public int getTreeQuantity(int i) {
        query = sentQueryToMongo();
        return query.get(i).getInteger("quantity");
    }

    public double getTreePrice(int i) {
        query = sentQueryToMongo();
        return query.get(i).getDouble("price");
    }

}

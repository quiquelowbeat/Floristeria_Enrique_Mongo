package entities;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.db;

public class Ticket {

    private int numTicket;
    private LocalDate date;
    private List<Product> products;
    private double total;
    private MongoCollection<Document> ticketsMongo = db.getCollection("tickets");
    private List<Document> query = ticketsMongo.find().into(new ArrayList<>());

    public Ticket() {
        numTicket = (!query.isEmpty()) ? (query.get(query.size() - 1).getInteger("_id") + 1) : 1;
        date = LocalDate.now();
        products = new ArrayList<>();
        total = 0;
    }

    public Ticket(LocalDate date) {
        numTicket = (!query.isEmpty()) ? (query.get(query.size() - 1).getInteger("_id") + 1) : 1;
        this.date = date;
        products = new ArrayList<>();
        total = 0;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void testProducts(){
        for(Product p : products){
            if(p instanceof Tree tree){
                System.out.println(tree.showInfo());
            } else if (p instanceof Flower flower){
                System.out.println(flower.showInfo());
            } else if (p instanceof Decor decor){
                System.out.println(decor.showInfo());
            }
        }
    }
    public double getTotal() {
        return total;
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addTest(){
        products.add(new Tree("manzano", 34, 2));

    }

}

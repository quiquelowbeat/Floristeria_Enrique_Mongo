package repositories;

import com.mongodb.client.MongoCollection;
import entities.Decor;
import entities.Flower;
import entities.Ticket;
import entities.Tree;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.db;

public class TicketRepository {

    private TreeRepository treeRepository;
    private FlowerRepository flowerRepository;
    private DecorRepository decorRepository;
    private MongoCollection<Document> ticketsMongo;
    private List<Document> query;

    public TicketRepository(TreeRepository treeRepository,FlowerRepository flowerRepository,DecorRepository decorRepository) {

        this.ticketsMongo = db.getCollection("tickets");
        this.treeRepository = treeRepository;
        this.flowerRepository = flowerRepository;
        this.decorRepository = decorRepository;

    }

    public Ticket createTicket(LocalDate date){
        return new Ticket(date);
    }

    public List<Document> queryToMongo(){

        return ticketsMongo.find().into(new ArrayList<>());

    }
    public Ticket createTicket(){
        return new Ticket();
    }

    public boolean addTicket(Ticket ticket){
        List<Document> products = new ArrayList<>();
        ticket.getProducts().forEach(product -> {
            if(product instanceof Tree){
                Document treeDoc = new Document();
                treeDoc.put("name", product.getName());
                treeDoc.put("size", ((Tree) product).getSize());
                treeDoc.put("price", product.getPrice());
                treeDoc.put("quantity", product.getQuantity());
                products.add(treeDoc);
            } else if(product instanceof Flower){
                Document flowerDoc = new Document();
                flowerDoc.put("name", product.getName());
                flowerDoc.put("color", ((Flower) product).getColor());
                flowerDoc.put("price", product.getPrice());
                flowerDoc.put("quantity", product.getQuantity());
                products.add(flowerDoc);
            } else if(product instanceof Decor){
                Document decorDoc = new Document();
                decorDoc.put("name", product.getName());
                decorDoc.put("material", ((Decor) product).getTypeOfMaterial());
                decorDoc.put("price", product.getPrice());
                decorDoc.put("quantity", product.getQuantity());
                products.add(decorDoc);
            }
        });

        Document ticketDB = new Document();
        ticketDB.put("_id", ticket.getNumTicket());
        ticketDB.put("date", ticket.getDate());
        ticketDB.put("products", products);
        ticketDB.put("total", ticket.getTotal());
        ticketsMongo.insertOne(ticketDB);

        return true;
    }

    public Ticket findOne(int i) {
        return findAll().get(i);

    }

    public List<Ticket> findAll(){
        query = queryToMongo();
        List<Ticket> allTickets = new ArrayList<>();
        query.forEach(document -> {
            List<Document> products = null;
            Ticket ticket = new Ticket();

            ticket.setNumTicket(document.getInteger("_id"));
            ticket.setDate(document.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            ticket.setTotal(document.getDouble("total"));
            products = document.getList("products", Document.class);

            for(Document doc : products){
                if(doc.getString("size") != null){
                    Tree tree = new Tree(doc.getString("name"), doc.getDouble("price"), doc.getInteger("quantity"));
                    tree.setSizeString(doc.getString("size"));
                    ticket.getProducts().add(tree);
                } else if(doc.getString("color") != null){
                    Flower flower = new Flower(doc.getString("name"), doc.getString("color"), doc.getDouble("price"),
                            doc.getInteger("quantity"));
                    ticket.getProducts().add(flower);
                } else if(doc.getString("material") != null){
                    Decor decor = new Decor(doc.getString("name"), doc.getDouble("price"),doc.getInteger("quantity"));
                    decor.setTypeOfMaterial(doc.getString("material"));
                    ticket.getProducts().add(decor);
                }
            }

            allTickets.add(ticket);
        });
        return allTickets;

    }

    public List<Ticket> getOldSales(LocalDate date) {
        List<Ticket> oldTickets = new ArrayList<>();

        for (int i = 0; i < findAll().size(); i++) {
            if (findOne(i).getDate().compareTo(date) <= 0) {
                oldTickets.add(findOne(i));
            }
        }
        return oldTickets;
    }

    public List<Ticket> getOldSales(LocalDate date1, LocalDate date2){
        List<Ticket> oldTickets = new ArrayList<>();

        for (int i = 0; i < findAll().size(); i++){
            if(findOne(i).getDate().compareTo(date2) < 0 && findOne(i).getDate().compareTo(date1) > 0 ){
                oldTickets.add(findOne(i));
            }
        }
        return oldTickets;
    }

    public List<Double> getTotalPricesFromDatabase(){
        List<Double> totalSalesList = new ArrayList<>();

        for(int i = 0; i < findAll().size(); i++){
            totalSalesList.add(findAll().get(i).getTotal());
        }
        return totalSalesList;
    }

}

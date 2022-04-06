package repositories;

import com.mongodb.client.MongoCollection;
import entities.Ticket;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.db;

public class TicketRepository {

    private MongoCollection<Document> ticketsMongo;

    public TicketRepository() {

        this.ticketsMongo = db.getCollection("tickets");

    }
    /*
    public Ticket createTicket(LocalDate date){
        return new Ticket(date);
    }
    */
    public Ticket createTicket(){
        return new Ticket();
    }

    public boolean addTicket(Ticket ticket){
        return database.getTickets().add(ticket);
    }

    public Ticket findOne(int i){

        return database.getTickets().get(i);

    }

    public List<Ticket> findAll(){

        return database.getTickets();

    }

    public List<Ticket> getOldSales(LocalDate date1) {

        List<Ticket> oldTickets = new ArrayList<>();

        for (int i = 0; i < findAll().size(); i++) {

            if (findOne(i).getDate().compareTo(date1) <= 0) {

                oldTickets.add(findOne(i));

            }
        }

        return oldTickets;
    }

    public List<Ticket> getOldSales(LocalDate date1, LocalDate date2){

        List<Ticket> oldTickets = new ArrayList<>();

        for (int i = 0; i<findAll().size(); i++){

            if(findOne(i).getDate().compareTo(date2) < 0 && findOne(i).getDate().compareTo(date1) > 0 ){

                oldTickets.add(findOne(i));

            }
        }

        return oldTickets;
    }

    public List<Double> getTotalPricesFromDatabase(){

        List<Double> totalSalesList = new ArrayList<>();

        for(int i = 0; i<database.getTickets().size(); i++){
            totalSalesList.add(database.getTickets().get(i).getTotal());

        }
        return totalSalesList;

    }

}

package services;

import entities.*;
import org.bson.Document;
import repositories.DecorRepository;
import repositories.FlowerRepository;
import repositories.TicketRepository;
import repositories.TreeRepository;

public class TicketService {

    private TicketRepository ticketRepository;
    private TreeRepository treeRepository;
    private FlowerRepository flowerRepository;
    private DecorRepository decorRepository;

    public TicketService ( TicketRepository ticketRepository, TreeRepository treeRepository,
                           FlowerRepository flowerRepository, DecorRepository decorRepository) {
        this.ticketRepository = ticketRepository;
        this.treeRepository = treeRepository;
        this.flowerRepository = flowerRepository;
        this.decorRepository = decorRepository;
    }

    public int addProductTree(Ticket ticket, String name, String size, int quantity) {
        int result = 0;
        Document treeDoc = treeRepository.findByNameAndSize(name, size);
        if (treeDoc != null) {
            if((treeDoc.getInteger("quantity") >= quantity)){
                Tree tree = new Tree(treeDoc.getString("name"), treeDoc.getDouble("price"), quantity);
                tree.setSizeString(treeDoc.getString("size"));
                ticket.getProducts().add(tree);
                treeRepository.removeTree(name, size, quantity);
                result = 1;
            } else {
                result = 2;
            }
        }
        return result;
    }
    public int addProductFlower(Ticket ticket, String name, String color, int quantity) {
        int result = 0;
        Document flowerDoc = flowerRepository.findByNameAndColor(name, color);
        if (flowerDoc != null) {
            if(flowerDoc.getInteger("quantity") >= quantity){
                Flower flower = new Flower(flowerDoc.getString("name"), flowerDoc.getString("color"), flowerDoc.getDouble("price"), quantity);
                ticket.getProducts().add(flower);
                flowerRepository.removeFlower(name, color, quantity);
                result = 1;
            } else {
                result = 2;
            }
        }
        return result;
    }
    public int addProductDecor(Ticket ticket, String name, String material, int quantity) {
        int result = 0;
        Document decorDoc = decorRepository.findByNameAndMaterial(name, material);
        if (decorDoc != null) {
            if(decorDoc.getInteger("quantity") >= quantity){
                Decor decor = new Decor(decorDoc.getString("name"), decorDoc.getDouble("price"), quantity);
                decor.setTypeOfMaterial(decorDoc.getString("material"));
                ticket.getProducts().add(decor);
                flowerRepository.removeFlower(name, material, quantity);
                result = 1;
            } else {
                result = 2;
            }
        }
        return result;
    }

    public void total(Ticket ticket) {

        double totalPriceTicket = 0;

        for (Product product : ticket.getProducts()) {

            totalPriceTicket += product.getPrice() * product.getQuantity();
        }

        ticket.setTotal(totalPriceTicket);
    }

    public double getTotalSales(){
        double totalSales = 0;
        for(Double totalPrice : ticketRepository.getTotalPricesFromDatabase()){
            totalSales += totalPrice;
        }
        return totalSales;

    }

}

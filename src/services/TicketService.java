package services;

import entities.Product;
import entities.Ticket;
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

    public boolean addProduct(Ticket ticket, String name) {

        boolean result = false;
        Product tree = treeRepository.findOne(name);
        Product flower = flowerRepository.findOne(name);
        Product decor = decorRepository.findOne(name);
        if(tree != null ){
            ticket.getProducts().add(tree);
            treeRepository.removeTree(name);
            result = true;
        }else if(flower != null){
            ticket.getProducts().add(flower);
            flowerRepository.removeFlower(name);
            result = true;
        }else if(decor != null){
            ticket.getProducts().add(decor);
            decorRepository.removeDecor(name);
            result = true;
        }
        return result;
    }

    public void total(Ticket ticket) {

        double totalPriceTicket = 0;

        for (Product product : ticket.getProducts()) {

            totalPriceTicket += product.getPrice();
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

import com.mongodb.diagnostics.logging.Loggers;
import database.DBConnection;
import entities.Decor;
import entities.Florist;
import entities.Ticket;
import entities.Tree;
import repositories.DecorRepository;
import repositories.FlowerRepository;
import repositories.TicketRepository;
import repositories.TreeRepository;
import services.FloristService;
import services.TicketService;
import tools.Keyboard;
import vista.View;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class App {

    public static void main(String[] args) {

        Logger.getLogger(Loggers.PREFIX).setLevel(Level.WARNING); // Oculta los mensajes de logging de MongoDB.
        DBConnection dbConnection = DBConnection.getInstance();

        Florist florist = new Florist("Margarita", "C/ Peru 254", "698574526");

        TreeRepository treeRepository = new TreeRepository();
        FlowerRepository flowerRepository = new FlowerRepository();
        DecorRepository decorRepository = new DecorRepository();
        TicketRepository ticketRepository = new TicketRepository(treeRepository,flowerRepository,decorRepository);

        FloristService floristService = new FloristService(treeRepository,flowerRepository,decorRepository,ticketRepository);
        TicketService ticketService = new TicketService(ticketRepository,treeRepository, flowerRepository, decorRepository);

        //------------- Menu ------------------

        int choice;
        do{
            View.options();
            choice = Keyboard.readInt("");
            if (!isBetween0And12(choice)){
                View.options();
                choice = Keyboard.readInt("");
            }else{
                switch (choice){

                    case 0:
                        View.closedSoftware();
                        break;

                    case 1:
                        boolean select;
                        Tree tree = treeRepository.createTree(Keyboard.readString("ENTER NAME"),
                                                                    Keyboard.readDouble("ENTER PRICE"),
                                                                    Keyboard.readInt("ENTER QUANTITY"));
                        do{
                            select = tree.setSize(Keyboard.readInt("""
                                                                        SIZE:\s
                                                                        1-SMALL
                                                                        2-MEDIUM
                                                                        3-BIG"""));
                            if(!select){View.showMessage("SELECT SIZE 1, 2 OR 3");}
                        }while (!select);
                        View.treeAdded(treeRepository.addTree(tree));
                        break;

                    case 2:
                        View.flowerAdded(flowerRepository.addFlower(flowerRepository.createFlower(Keyboard.readString("ENTER NAME"),
                                                                                                    Keyboard.readString("ENTER COLOR"),
                                                                                                    Keyboard.readDouble("ENTER PRICE"),
                                                                                                    Keyboard.readInt("ENTER QUANTITY"))));
                        break;

                    case 3:
                        Decor decor = decorRepository.createDecor(Keyboard.readString("ENTER NAME"), Keyboard.readDouble("ENTER PRICE"), Keyboard.readInt("ENTER QUANTITY"));
                        do{
                            select = decor.setTypeOfMaterialMenu(Keyboard.readInt("""
                                                                        MATERIAL:\s
                                                                        1-WOOD
                                                                        2-PLASTIC"""));
                            if(!select){View.showMessage("SELECT 1 OR 2");}
                        }while (!select);
                        View.decorAdded(decorRepository.addDecor(decor));
                        break;
                    case 4:
                        View.showStock(floristService.getTrees(), floristService.getFlowers(), floristService.getDecorations());
                        break;

                    case 5:
                        View.showRemoveMessageConfirmation(treeRepository.removeTree(Keyboard.readString("ENTER NAME"), Keyboard.readString("ENTER SIZE"), Keyboard.readInt("ENTER QUANTITY TO REMOVE")));
                        break;

                    case 6:
                        View.showRemoveMessageConfirmation(flowerRepository.removeFlower(Keyboard.readString("ENTER NAME"), Keyboard.readString("ENTER COLOR"), Keyboard.readInt("ENTER QUANTITY TO REMOVE")));
                        break;

                    case 7:
                        View.showRemoveMessageConfirmation(decorRepository.removeDecor(Keyboard.readString("ENTER NAME"), Keyboard.readString("ENTER MATERIAL"), Keyboard.readInt("ENTER QUANTITY TO REMOVE")));
                        break;

                    case 8:
                        floristService.createStockFlorist(florist);
                        View.showStockByProduct(florist.getProducts());
                        break;

                    case 9:
                        View.showTotalValueFlorist(floristService.getTotalValue());
                        break;

                    case 10:
                        String x;
                        Ticket ticket = ticketRepository.createTicket();
                        do {
                            x = Keyboard.readString("""
                                                        1-ADD TREE
                                                        2-ADD FLOWER
                                                        3-ADD DECOR
                                                        0-EXIT
                                                        """);
                            switch(x){
                                case "1":
                                    View.productAdded(ticketService.addProductTree(ticket, Keyboard.readString("ENTER NAME"), Keyboard.readString("ENTER SIZE"), Keyboard.readInt("ENTER QUANTITY")));
                                    break;
                                case "2":
                                    View.productAdded(ticketService.addProductFlower(ticket, Keyboard.readString("ENTER NAME"), Keyboard.readString("ENTER COLOR"), Keyboard.readInt("ENTER QUANTITY")));
                                    break;
                                case "3":
                                    View.productAdded(ticketService.addProductDecor(ticket, Keyboard.readString("ENTER NAME"), Keyboard.readString("ENTER MATERIAL"), Keyboard.readInt("ENTER QUANTITY")));
                                    break;
                            }
                        }while (!x.equalsIgnoreCase("0"));
                        if (!ticket.getProducts().isEmpty()){
                            ticketService.total(ticket);
                            View.ticketAdded(ticketRepository.addTicket(ticket));
                        }else {
                            View.showMessage("TICKET NOT ADDED, PRODUCT LIST EMPTY");
                        }
                        break;

                    case 11:
                        LocalDate date = LocalDate.of(Keyboard.readInt("Year YYYY"), Keyboard.readInt("MONTH MM"), Keyboard.readInt("DAY DD"));
                        View.showOldTickets(ticketRepository.getOldSales(date));
                        break;

                    case 12:
                        View.showTotalSales(ticketService.getTotalSales());
                        break;
                }

                System.out.println("--------------------------------------------");

            }
        }while (choice!=0);

    }

    static boolean isBetween0And12(int choice){
        return (choice >= 0) && (choice <=12);
    }

}

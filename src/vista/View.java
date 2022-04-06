package vista;

import entities.*;

import java.util.HashMap;
import java.util.List;


public class View {

    public static void showStock(List<Product> trees, List<Product> flowers, List<Product> decorations) {
        System.out.println("TREES:");
        if(!trees.isEmpty()){
            trees.forEach(x -> {
                Tree tree = (Tree) x;
                System.out.println(tree.showInfo());
            });
        } else {
            System.out.println("NO STOCK\n");
        }
        System.out.println("FLOWERS:");
        if(!flowers.isEmpty()){
            flowers.forEach(x -> {
                Flower flower = (Flower) x;
                System.out.println(flower.showInfo());
            });
        } else {
            System.out.println("NO STOCK\n");
        }
        System.out.println("DECORATIONS:");
        if(!decorations.isEmpty()){
            decorations.forEach(x -> {
                Decor decor = (Decor) x;
                System.out.println(decor.showInfo());
            });
        } else {
            System.out.println("NO STOCK\n");
        }
    }

    public static void showTotalValueFlorist(double totalValue) {

        System.out.println("TOTAL VALUE: " + totalValue + "€");

    }

    public static void showStockByProduct(HashMap<String, Integer> stockProducts) {
        System.out.println("TOTAL STOCK:");
        System.out.println("TREES STOCK: " + stockProducts.get("Trees"));
        System.out.println("FLOWERS STOCK: " + stockProducts.get("Flowers"));
        System.out.println("DECOR STOCK: " + stockProducts.get("Decorations"));
    }

    public static void showInfoTicket(Ticket ticket) {

        System.out.println("TICKET NUMBER: " + ticket.getNumTicket() +
                            "\nDATE: " + ticket.getDate());

        for (Product product : ticket.getProducts()) {

            if (product instanceof Tree) {

                Tree tree = (Tree) product;
                System.out.println(tree.showInfo());

            } else if (product instanceof Flower) {

                Flower flower = (Flower) product;
                System.out.println(flower.showInfo());

            } else if (product instanceof Decor) {

                Decor decor = (Decor) product;
                System.out.println(decor.showInfo());
            }
        }

        System.out.println("TICKET TOTAL: " + ((double) Math.round(ticket.getTotal() * 100d) / 100d) + "€");
    }

    public static void showOldTickets(List<Ticket> oldTickets) {

        oldTickets.forEach(x -> showInfoTicket(x));

    }

    public static void showRemoveMessageConfirmation(int option) {
        if(option == 0){
            System.out.println("PRODUCT NOT FOUND.");
        } else if(option == 1) {
            System.out.println("PRODUCT SUCCESSFULLY REMOVED.");
        } else if(option == 2){
            System.out.println("QUANTITY EXCEEDS THE AVAILABLE STOCK.");
        }
    }

    public static void showTotalSales(double totalSales) {

        System.out.println("TOTAL SALES: " + ((double) Math.round(totalSales * 100d) / 100d) + "€");

    }

    public static void options() {
        System.out.println("SELECT OPTION 0 - 12:");
        System.out.println("""
                1-ADD TREE.
                2-ADD FLOWER.
                3-ADD DECOR.
                4-SHOW STOCK.
                5-REMOVE TREE.
                6-REMOVE FLOWER.
                7-REMOVE DECOR.
                8-SHOW STOCK QUANTITY
                9-TOTAL VALUE
                10-CREATE TICKET
                11-SHOW OLD TICKETS
                12-SHOW TOTAL MONEY
                0-EXIT.""");
    }

    public static void treeAdded ( boolean result){
        if (result) {
            System.out.println("TREE SUCCESSFULLY ADDED.");
        } else {
            System.out.println("TREE NOT ADDED.");
        }
    }

    public static void flowerAdded ( boolean result){
        if (result) {
            System.out.println("FLOWER SUCCESSFULLY ADDED.");
        } else {
            System.out.println("FLOWER NOT ADDED.");
        }
    }

    public static void decorAdded ( boolean result){
        if (result) {
            System.out.println("DECORATION SUCCESSFULLY ADDED.");
        } else {
            System.out.println("DECORATION NOT ADDED.");
        }
    }

    public static void productAdded ( boolean result){
        if (result) {
            System.out.println("PRODUCT SUCCESSFULLY ADDED.");
        } else {
            System.out.println("PRODUCT NOT FOUND.");
        }
    }

    public static void ticketAdded ( boolean result){
        if (result) {
            System.out.println("TICKET SUCCESSFULLY ADDED.");
        } else {
            System.out.println("TICKET NOT FOUND.");
        }
    }

    public static void showMessage (String message){
        System.out.println(message);
    }

    public static void closedSoftware () {
        System.out.println("SOFTWARE SUCCESSFULLY CLOSED");
    }

    public static void formatError () {
        System.out.println("FORMAT ERROR");
    }

    public static void invalidInformation () {
        System.out.println("INPUT NOT VALID");
    }

    public static void introductionErrorString () {
        System.out.println("ERROR INPUT STRING.");

    }

    public static void fileNotFound(){
        System.out.println("FILE NOT FOUND.");
    }

}



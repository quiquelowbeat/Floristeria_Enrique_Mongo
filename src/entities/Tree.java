package entities;

public class Tree extends Product {

    private String size;

    public Tree(String name, double price, int quantity){
        super(name, price, quantity);
    }

    public String getSize() {
        return size;
    }

    public boolean setSize(int num) {
        boolean select = false;
        if(num == 1){
            this.size = "Small";
            select = true;
        } else if (num == 2){
            this.size = "Medium";
            select = true;
        } else if (num == 3){
            this.size = "Big";
            select = true;
        }
        return select;
    }

    public void setSizeString(String size){
        this.size = size;
    }

    @Override
    public String showInfo() {
        return "Name: " + super.getName() + "\nSize: " + this.size + "\nPrice: " + super.getPrice() + "€\nQuantity: " + super.getQuantity() + "\n";
    }
}

package entities;

public class Flower extends Product {

    private String color;

    public Flower(String name, String color, double price, int quantity){
        super(name, price, quantity);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String showInfo() {
        return "Name: " + super.getName() + "\nColor: " + this.color + "\nPrice: " + super.getPrice() + "€\nQuantity: " + super.getQuantity() + "\n";
    }
}

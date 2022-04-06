package entities;

public class Decor extends Product {

    private String typeOfMaterial;

    public Decor(String name, double price, int quantity){
        super(name, price, quantity);
    }

    public boolean setTypeOfMaterialMenu(int type) {
        boolean select = false;
        if(type == 1) {
            typeOfMaterial = "WOOD";
            select = true;
        }else if(type == 2){
            typeOfMaterial = "PLASTIC";
            select = true;
        }
        return select;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    @Override
    public String showInfo() {
        return "Name: " + super.getName() + "\nMaterial: " + this.typeOfMaterial + "\nPrice: " + super.getPrice() + "€\nQuantity: " + super.getQuantity() + "\n";
    }
}

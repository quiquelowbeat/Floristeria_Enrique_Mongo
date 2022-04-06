package entities;

import java.io.Serializable;
import java.util.Objects;

public abstract class Product implements Serializable {

    static int nextId = 1;

    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity){

        this.id = nextId;
        nextId++;
        this.name = name;
        this.price = price;
        this.quantity = quantity;

    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract String showInfo();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

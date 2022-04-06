package entities;

import java.util.HashMap;

public class Florist {

    static int idGenerator = 1;
    private int idFlorist;
    private String name;
    private String address;
    private String phoneNum;
    public HashMap<String, Integer> products;


    public Florist(String name, String address, String phoneNum) {
        idFlorist = idGenerator++;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        products = new HashMap<>();

    }

    public int getIdFlorist() {
        return idFlorist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }
}

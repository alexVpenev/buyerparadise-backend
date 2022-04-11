package individualProjectAleksandarPenev.BuyersParadise.model;

import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import lombok.Getter;
import lombok.Setter;

public class Offer implements IOffer {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private int seller_id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private double price;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String seller_name;

    public Offer() {

    }

    public Offer(int id, int seller_id, String name, double price, String description, String seller_name) {
        this.id = id;
        this.seller_id = seller_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.seller_name = seller_name;
    }


    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description=" + description +
                '}';
    }

}

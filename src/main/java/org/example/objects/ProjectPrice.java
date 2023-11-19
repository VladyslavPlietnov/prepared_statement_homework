package org.example.objects;

public class ProjectPrice {
public int name;
public double price;

    public ProjectPrice(int name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProjectPrice{" +
                "name=" + name +
                ", price=" + price +
                '}';
    }
}

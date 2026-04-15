package uz.itpu.danial_sarsenov.entity;

import java.util.List;

public final class Warehouse extends BaseEntity {
    private final String name;
    private final String location;
    private final List<Product> products;

    public Warehouse(int id, String name, String location, List<Product> products) {
        super(id);
        this.name = name;
        this.location = location;
        this.products = products;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Product> getProducts() { return products; }

    @Override
    public String toString() {
        return "Warehouse{id=" + getId() + ", name='" + name +
                "', location='" + location + "', products=" + products.size() + "}";
    }
}

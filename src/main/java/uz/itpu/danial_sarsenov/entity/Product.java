package uz.itpu.danial_sarsenov.entity;

public class Product extends BaseEntity {

    private final String name;
    private final String category;
    private final double price;
    private final int quantity;
    private final String material;

    public Product(int id,
                   String name,
                   String category,
                   double price,
                   int quantity,
                   String material) {
        super(id);
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.material = material;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getMaterial() { return material; }

    @Override
    public String toString() {
        return "Appliance{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
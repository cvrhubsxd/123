package uz.itpu.danial_sarsenov.entity;

public class Product extends BaseEntity {
    private final String name;
    private final String category;
    private final double price;
    private final int quantity;
    private final String material;
    private final String type; // Electric / Cooling
    private final double power; // kW
    private final int voltage;

    public Product(int id, String name, String category, double price, int quantity, String material, String type, double power, int voltage) {
        super(id);
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.material = material;
        this.type = type;
        this.power = power;
        this.voltage = voltage;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getMaterial() { return material; }
    public String getType() { return type; }
    public double getPower() { return power; }
    public int getVoltage() { return voltage; }

    @Override
    public String toString() {
        return "Appliance{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                ", power=" + power +
                ", voltage=" + voltage +
                '}';
    }
}

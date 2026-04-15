package uz.itpu.danial_sarsenov.entity;

public final class Supplier extends BaseEntity {
    private final String name;
    private final String contact;

    public Supplier(int id, String name, String contact) {
        super(id);
        this.name = name;
        this.contact = contact;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return "Supplier{id=" + getId() + ", name='" + name + "', contact='" + contact + "'}";
    }
}

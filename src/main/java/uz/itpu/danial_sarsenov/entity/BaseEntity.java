package uz.itpu.danial_sarsenov.entity;

public abstract class BaseEntity implements Identifiable {
    private final int id;

    protected BaseEntity(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}

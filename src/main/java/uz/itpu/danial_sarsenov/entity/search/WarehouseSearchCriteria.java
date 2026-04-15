package uz.itpu.danial_sarsenov.entity.search;

import uz.itpu.danial_sarsenov.entity.Warehouse;

public class WarehouseSearchCriteria implements SearchCriteria<Warehouse> {
    private final String location;

    public WarehouseSearchCriteria(String location) {
        this.location = location;
    }

    @Override
    public boolean matches(Warehouse warehouse) {
        return warehouse != null && (location == null || warehouse.getLocation().toLowerCase().contains(location.toLowerCase()));
    }
}

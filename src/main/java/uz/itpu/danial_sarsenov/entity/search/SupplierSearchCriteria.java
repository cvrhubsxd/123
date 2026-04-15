package uz.itpu.danial_sarsenov.entity.search;

import uz.itpu.danial_sarsenov.entity.Supplier;

public class SupplierSearchCriteria implements SearchCriteria<Supplier> {
    private final String name;

    public SupplierSearchCriteria(String name) {
        this.name = name;
    }

    @Override
    public boolean matches(Supplier supplier) {
        return supplier != null && (name == null || supplier.getName().toLowerCase().contains(name.toLowerCase()));
    }
}

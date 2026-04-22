package uz.itpu.danial_sarsenov.dao;

import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.Supplier;
import uz.itpu.danial_sarsenov.exception.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDao extends FileBasedDao<Product> {

    private final Map<Integer, Supplier> supplierCache;

    public ProductDao(String filePath, List<Supplier> suppliers) {
        super(filePath);
        this.supplierCache = new HashMap<>();
        for (Supplier s : suppliers) supplierCache.put(s.getId(), s);
    }

    @Override
    protected Product parse(String line) {
        String[] parts = line.split(",");

        if (parts.length != 6) {
            throw new DaoException("Invalid product line: " + line, null);
        }

        try {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String category = parts[2].trim();
            double price = Double.parseDouble(parts[3].trim());
            int quantity = Integer.parseInt(parts[4].trim());
            int supplierId = Integer.parseInt(parts[5].trim());

            Supplier supplier = supplierCache.get(supplierId);
            if (supplier == null) {
                throw new DaoException("Supplier not found: " + supplierId, null);
            }

            return new Product(
                    id,
                    name,
                    category,
                    price,
                    quantity,
                    supplier.getName()
            );

        } catch (Exception e) {
            throw new DaoException("Failed to parse product line: " + line, e);
        }
    }
}
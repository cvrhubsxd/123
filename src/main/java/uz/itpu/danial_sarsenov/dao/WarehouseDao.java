package uz.itpu.danial_sarsenov.dao;

import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.Warehouse;
import uz.itpu.danial_sarsenov.exception.DaoException;

import java.util.*;
import java.util.stream.Collectors;

public class WarehouseDao extends FileBasedDao<Warehouse> {

    private final Map<Integer, Product> productCache;

    public WarehouseDao(String filePath, List<Product> products) {
        super(filePath);
        this.productCache = new HashMap<>();
        for (Product p : products) productCache.put(p.getId(), p);
    }

    @Override
    protected Warehouse parse(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new DaoException("Invalid warehouse line: " + line, null);
        }

        int id;
        try {
            id = Integer.parseInt(parts[0].trim());
        } catch (NumberFormatException e) {
            throw new DaoException("Invalid warehouse id: " + parts[0], e);
        }

        String name = parts[1].trim();
        String location = parts[2].trim();

        List<Product> products = Arrays.stream(parts[3].trim().split("\\|"))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(pid -> {
                    Product p = productCache.get(pid);
                    if (p == null) throw new DaoException("Product not found: " + pid, null);
                    return p;
                })
                .collect(Collectors.toList());

        return new Warehouse(id, name, location, products);
    }
}

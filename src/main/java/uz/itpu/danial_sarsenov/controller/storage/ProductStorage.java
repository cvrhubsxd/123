package uz.itpu.danial_sarsenov.controller.storage;

import uz.itpu.danial_sarsenov.entity.Product;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public final class ProductStorage {

    private static final List<Product> PRODUCTS = new ArrayList<>();

    private ProductStorage() {}

    public static void add(Product product) {
        PRODUCTS.add(product);
    }

    public static Collection<Product> getAll() {
        return Collections.unmodifiableList(PRODUCTS);
    }
}

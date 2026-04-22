package uz.itpu.danial_sarsenov.server.storage;

import uz.itpu.danial_sarsenov.entity.Product;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductRepository {

    private static final List<Product> PRODUCTS = new CopyOnWriteArrayList<>();

    public static void init(List<Product> initial) {
        PRODUCTS.clear();
        PRODUCTS.addAll(initial);
    }

    public static List<Product> findAll() {
        return List.copyOf(PRODUCTS);
    }

    public static void add(Product p) {
        PRODUCTS.add(p);
    }

    public static boolean remove(int id) {
        return PRODUCTS.removeIf(p -> p.getId() == id);
    }
}
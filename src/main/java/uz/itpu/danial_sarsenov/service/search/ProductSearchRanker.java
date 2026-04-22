package uz.itpu.danial_sarsenov.service.search;

import uz.itpu.danial_sarsenov.entity.Product;

import java.util.Comparator;

public class ProductSearchRanker {

    public static int score(Product p, String query) {
        if (query == null || query.isBlank()) return 0;

        String q = query.toLowerCase();

        String name = p.getName().toLowerCase();
        String category = p.getCategory().toLowerCase();

        if (name.equals(q)) return 3;
        if (name.startsWith(q)) return 2;
        if (name.contains(q)) return 1;

        if (category.contains(q)) return 1;

        return 0;
    }

    public static boolean matches(Product p, String query) {
        return score(p, query) > 0;
    }

    public static Comparator<Product> comparator(String query) {
        return (a, b) -> Integer.compare(
                score(b, query),
                score(a, query)
        );
    }
}
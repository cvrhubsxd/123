package uz.itpu.danial_sarsenov.service;

import uz.itpu.danial_sarsenov.entity.Product;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.Set;
import java.util.stream.Collectors;

public class StatsService {

    public String buildStats(Collection<Product> products) {
        if (products == null || products.isEmpty()) {
            return "Warehouse is empty";
        }

        int totalProducts = products.size();
        int totalQuantity = products.stream()
                .mapToInt(Product::getQuantity)
                .sum();

        DoubleSummaryStatistics priceStats = products.stream()
                .mapToDouble(Product::getPrice)
                .summaryStatistics();

        Set<String> categories = products.stream()
                .map(Product::getCategory)
                .collect(Collectors.toSet());

        return """
                Warehouse statistics:
                Total products: %d
                Total quantity: %d
                Min price: %.2f
                Max price: %.2f
                Average price: %.2f
                Categories count: %d
                """.formatted(
                totalProducts,
                totalQuantity,
                priceStats.getMin(),
                priceStats.getMax(),
                priceStats.getAverage(),
                categories.size()
        );
    }
}

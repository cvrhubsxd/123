package uz.itpu.danial_sarsenov.service;

import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.search.ProductSearchCriteria;
import uz.itpu.danial_sarsenov.service.search.ProductSearchRanker;

import java.util.List;

public class ProductSearchService {

    public List<Product> search(List<Product> products, ProductSearchCriteria criteria, String queryName) {

        List<Product> filtered = products.stream()
                .filter(criteria::matches)
                .toList();

        if (queryName != null && !queryName.isBlank()) {
            filtered = filtered.stream()
                    .filter(p -> ProductSearchRanker.matches(p, queryName))
                    .sorted(ProductSearchRanker.comparator(queryName))
                    .toList();
        }

        return filtered;
    }
}
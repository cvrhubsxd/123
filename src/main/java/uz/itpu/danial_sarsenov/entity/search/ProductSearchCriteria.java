package uz.itpu.danial_sarsenov.entity.search;

import uz.itpu.danial_sarsenov.entity.Product;

public class ProductSearchCriteria implements SearchCriteria<Product> {

    private final String name;
    private final String category;
    private final Double priceLow;
    private final Double priceHigh;

    public ProductSearchCriteria(String name,
                                 String category,
                                 Double priceLow,
                                 Double priceHigh) {
        this.name = name;
        this.category = category;
        this.priceLow = priceLow;
        this.priceHigh = priceHigh;
    }

    @Override
    public boolean matches(Product product) {
        if (product == null) return false;

        if (name != null &&
                !product.getName().toLowerCase().contains(name.toLowerCase()))
            return false;

        if (category != null &&
                !product.getCategory().toLowerCase().contains(category.toLowerCase()))
            return false;

        if (priceLow != null && product.getPrice() < priceLow)
            return false;

        if (priceHigh != null && product.getPrice() > priceHigh)
            return false;

        return true;
    }
}
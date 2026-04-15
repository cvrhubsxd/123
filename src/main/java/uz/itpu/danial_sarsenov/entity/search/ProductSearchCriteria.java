package uz.itpu.danial_sarsenov.entity.search;

import uz.itpu.danial_sarsenov.entity.Product;
import java.util.Objects;

public class ProductSearchCriteria implements SearchCriteria<Product> {

    private final String name;
    private final String category;
    private final Double priceLow;
    private final Double priceHigh;
    private final String energyType;

    public ProductSearchCriteria(String name, String category, Double priceLow, Double priceHigh, String energyType) {
        this.name = name;
        this.category = category;
        this.priceLow = priceLow;
        this.priceHigh = priceHigh;
        this.energyType = energyType;
    }

    @Override
    public boolean matches(Product product) {
        if (product == null) return false;

        if (name != null && !product.getName().toLowerCase().contains(name.toLowerCase())) return false;

        if (category != null && !product.getCategory().toLowerCase().contains(category.toLowerCase())) return false;

        if (energyType != null) {
            String etLower = energyType.toLowerCase();
            boolean matchesEnergy = (product.getType() != null && product.getType().toLowerCase().contains(etLower))
                    || (product.getCategory() != null && product.getCategory().toLowerCase().contains(etLower));
            if (!matchesEnergy) return false;
        }

        if (priceLow != null && product.getPrice() < priceLow) return false;

        if (priceHigh != null && product.getPrice() > priceHigh) return false;

        return true;
    }

}

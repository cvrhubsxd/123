package uz.itpu.danial_sarsenov.dao;

import uz.itpu.danial_sarsenov.entity.*;
import uz.itpu.danial_sarsenov.entity.search.SearchCriteria;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DaoFactory {
    private static final String SUPPLIERS = "suppliers.txt";
    private static final String PRODUCTS = "products.txt";
    private static final String WAREHOUSES = "warehouses.txt";


    private DaoFactory() {}

    public static SupplierDao getSupplierDao() {
        return new SupplierDao(SUPPLIERS);
    }

    public static ProductDao getProductDao(List<Supplier> suppliers) {
        return new ProductDao(PRODUCTS, suppliers);
    }

    public static WarehouseDao getWarehouseDao(List<Product> products) {
        return new WarehouseDao(WAREHOUSES, products);
    }


    public static <T extends BaseEntity> List<T> findEntities(
            List<T> entities,
            SearchCriteria<? super T> criteria
    ) {
        return entities.stream()
                .filter(criteria::matches)
                .toList();
    }


    private static final Map<Class<?>, List<? extends BaseEntity>> ENTITY_CACHE = new HashMap<>();

    public static <T extends BaseEntity> void cacheEntities(Class<T> clazz, List<T> entities) {
        ENTITY_CACHE.put(clazz, entities);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseEntity> List<T> getCachedEntities(Class<T> clazz) {
        return (List<T>) ENTITY_CACHE.getOrDefault(clazz, List.of());
    }
}

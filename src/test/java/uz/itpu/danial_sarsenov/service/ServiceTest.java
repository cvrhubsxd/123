package uz.itpu.danial_sarsenov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.itpu.danial_sarsenov.dao.EntityDao;
import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.search.AnySearchCriteria;
import uz.itpu.danial_sarsenov.entity.search.ProductSearchCriteria;
import uz.itpu.danial_sarsenov.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityServiceTest {

    private EntityDao<Product> productDao;
    private EntityService<Product> productService;

    private Product p1;
    private Product p2;

    @BeforeEach
    void setup() {
        p1 = new Product(1, "Toaster", "Kitchen", 50.0, 10, "Metal", "Electric", 1.2, 220);
        p2 = new Product(2, "Fridge", "Cooling", 300.0, 5, "Metal", "Cooling", 2.0, 220);


        productDao = new EntityDao<>() {
            private final List<Product> products = List.of(p1, p2);

            @Override
            public List<Product> find() {
                return new ArrayList<>(products);
            }
        };

        productService = new EntityServiceImpl<>(productDao);
    }

    @Test
    void findAll_shouldReturnAllProducts() throws ServiceException {
        List<Product> all = productService.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(p1) && all.contains(p2));
    }

    @Test
    void find_withCriteria_shouldFilterCorrectly() throws ServiceException {
        ProductSearchCriteria criteria = new ProductSearchCriteria("toaster", null, null, null, null);
        List<Product> filtered = productService.find(criteria);
        assertEquals(1, filtered.size());
        assertEquals(p1, filtered.get(0));
    }

    @Test
    void find_withAnyCriteria_shouldReturnAll() throws ServiceException {
        AnySearchCriteria<Product> criteria = new AnySearchCriteria<>();
        List<Product> filtered = productService.find(criteria);
        assertEquals(2, filtered.size());
    }

}

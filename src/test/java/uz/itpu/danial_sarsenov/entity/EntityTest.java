package uz.itpu.danial_sarsenov.entity;

import org.junit.jupiter.api.Test;
import uz.itpu.danial_sarsenov.entity.search.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void baseEntity_shouldReturnId() {
        BaseEntity entity = new BaseEntity(42) {};
        assertEquals(42, entity.getId());
    }

    @Test
    void product_gettersAndToString() {
        Product p = new Product(1, "Toaster", "Kitchen", 50.0, 10, "Metal", "Electric", 1.2, 220);
        assertEquals("Toaster", p.getName());
        assertEquals("Kitchen", p.getCategory());
        assertEquals(50.0, p.getPrice());
        assertEquals(10, p.getQuantity());
        assertEquals("Metal", p.getMaterial());
        assertEquals("Electric", p.getType());
        assertEquals(1.2, p.getPower());
        assertEquals(220, p.getVoltage());
        assertTrue(p.toString().contains("Toaster"));
    }

    @Test
    void supplier_gettersAndToString() {
        Supplier s = new Supplier(2, "BestSupplier", "contact@mail.com");
        assertEquals("BestSupplier", s.getName());
        assertEquals("contact@mail.com", s.getContact());
        assertTrue(s.toString().contains("BestSupplier"));
    }

    @Test
    void warehouse_gettersAndToString() {
        Product p1 = new Product(1, "Toaster", "Kitchen", 50.0, 10, "Metal", "Electric", 1.2, 220);
        Product p2 = new Product(2, "Fridge", "Cooling", 300.0, 5, "Metal", "Cooling", 2.0, 220);
        Warehouse w = new Warehouse(1, "MainWarehouse", "City", List.of(p1, p2));
        assertEquals("MainWarehouse", w.getName());
        assertEquals("City", w.getLocation());
        assertEquals(2, w.getProducts().size());
        assertTrue(w.toString().contains("MainWarehouse"));
    }

    @Test
    void anySearchCriteria_shouldAlwaysMatch() {
        AnySearchCriteria<Object> any = new AnySearchCriteria<>();
        assertTrue(any.matches(null));
        assertTrue(any.matches(new Object()));
    }

    @Test
    void productSearchCriteria_shouldMatchCorrectly() {
        Product p = new Product(1, "Toaster", "Kitchen", 50.0, 10, "Metal", "Electric", 1.2, 220);

        ProductSearchCriteria byName = new ProductSearchCriteria("toaster", null, null, null, null);
        assertTrue(byName.matches(p));

        ProductSearchCriteria byCategory = new ProductSearchCriteria(null, "kitchen", null, null, null);
        assertTrue(byCategory.matches(p));

        ProductSearchCriteria byEnergy = new ProductSearchCriteria(null, null, null, null, "electric");
        assertTrue(byEnergy.matches(p));

        ProductSearchCriteria byPriceRange = new ProductSearchCriteria(null, null, 40.0, 60.0, null);
        assertTrue(byPriceRange.matches(p));

        ProductSearchCriteria notMatching = new ProductSearchCriteria("fridge", null, null, null, null);
        assertFalse(notMatching.matches(p));
    }

    @Test
    void supplierSearchCriteria_shouldMatchCorrectly() {
        Supplier s = new Supplier(1, "BestSupplier", "contact@mail.com");
        SupplierSearchCriteria byName = new SupplierSearchCriteria("best");
        assertTrue(byName.matches(s));

        SupplierSearchCriteria notMatching = new SupplierSearchCriteria("other");
        assertFalse(notMatching.matches(s));
    }

    @Test
    void warehouseSearchCriteria_shouldMatchCorrectly() {
        Warehouse w = new Warehouse(1, "MainWarehouse", "City", List.of());
        WarehouseSearchCriteria byLocation = new WarehouseSearchCriteria("city");
        assertTrue(byLocation.matches(w));

        WarehouseSearchCriteria notMatching = new WarehouseSearchCriteria("village");
        assertFalse(notMatching.matches(w));
    }
}

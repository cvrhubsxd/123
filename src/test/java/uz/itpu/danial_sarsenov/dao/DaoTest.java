package uz.itpu.danial_sarsenov.dao;

import org.junit.jupiter.api.Test;
import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.Supplier;
import uz.itpu.danial_sarsenov.exception.DaoException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {

    @Test
    void find_shouldReturnSuppliers_whenFileIsValid() throws DaoException {
        SupplierDao dao = new SupplierDao("suppliers.txt");
        List<Supplier> suppliers = dao.find();
        assertFalse(suppliers.isEmpty());
    }

    @Test
    void parse_shouldThrowException_whenLineInvalid() {
        SupplierDao dao = new SupplierDao("suppliers.txt");
        String badLine = "1,OnlyName"; // мало колонок
        RuntimeException ex = assertThrows(RuntimeException.class, () -> dao.parse(badLine));
        assertTrue(ex.getMessage().contains("Invalid supplier line"));
    }
}

class ProductDaoTest {

    @Test
    void find_shouldReturnProducts_whenFileIsValid() throws DaoException {
        List<Supplier> suppliers = new SupplierDao("suppliers.txt").find();
        ProductDao dao = new ProductDao("products.txt", suppliers);
        List<Product> products = dao.find();
        assertFalse(products.isEmpty());
    }

    @Test
    void parse_shouldThrowException_whenSupplierMissing() {
        List<Supplier> suppliers = List.of(); // пустой список
        ProductDao dao = new ProductDao("products.txt", suppliers);
        String line = "1,Toaster,Kitchen,49.99,10,999"; // SupplierId 999 не существует

        DaoException ex = assertThrows(DaoException.class, () -> dao.parse(line));
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains("Supplier not found") || ex.getMessage().contains("Failed to parse product line"));
    }

}

class DaoFactoryTest {

    @Test
    void cacheAndGetCachedEntities_shouldWorkCorrectly() {
        List<Supplier> suppliers = List.of(new Supplier(1, "Sup1", "Desc"));
        DaoFactory.cacheEntities(Supplier.class, suppliers);

        List<Supplier> cached = DaoFactory.getCachedEntities(Supplier.class);
        assertEquals(1, cached.size());
        assertEquals("Sup1", cached.get(0).getName());
    }
}

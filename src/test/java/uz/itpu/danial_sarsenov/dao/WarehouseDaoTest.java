package uz.itpu.danial_sarsenov.dao;

import org.junit.jupiter.api.Test;
import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.Warehouse;
import uz.itpu.danial_sarsenov.exception.DaoException;
import uz.itpu.danial_sarsenov.entity.Supplier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDaoTest {

    @Test
    void find_shouldReturnWarehouses_whenFileIsValid() throws DaoException {
        List<Supplier> suppliers = new SupplierDao("suppliers.txt").find();
        List<Product> products = new ProductDao("products.txt", suppliers).find();
        WarehouseDao dao = new WarehouseDao("warehouses.txt", products);

        List<Warehouse> warehouses = dao.find();
        assertFalse(warehouses.isEmpty());
    }

    @Test
    void parse_shouldThrowException_whenProductIdInvalid() {
        List<Product> products = List.of(); // пустой список
        WarehouseDao dao = new WarehouseDao("warehouses.txt", products);

        String line = "1,MainWarehouse,City,999"; // ProductId 999 не существует

        DaoException ex = assertThrows(DaoException.class, () -> dao.parse(line));
        assertTrue(ex.getMessage().contains("Product not found"));
    }

}

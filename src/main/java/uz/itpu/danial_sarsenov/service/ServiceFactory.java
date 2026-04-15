package uz.itpu.danial_sarsenov.service;

import uz.itpu.danial_sarsenov.dao.ProductDao;
import uz.itpu.danial_sarsenov.dao.SupplierDao;
import uz.itpu.danial_sarsenov.dao.WarehouseDao;
import uz.itpu.danial_sarsenov.dao.DaoFactory;
import uz.itpu.danial_sarsenov.entity.*;

import java.util.List;

public class ServiceFactory {

    private static EntityService<Product> productService;
    private static EntityService<Supplier> supplierService;
    private static EntityService<Warehouse> warehouseService;

    private ServiceFactory() {}

    public static void init() throws Exception {
        // Создаем DAO
        SupplierDao supplierDao = DaoFactory.getSupplierDao();
        supplierService = new EntityServiceImpl<>(supplierDao);

        List<Supplier> suppliers = supplierService.findAll();
        ProductDao productDao = DaoFactory.getProductDao(suppliers);
        productService = new EntityServiceImpl<>(productDao);

        List<Product> products = productService.findAll();
        WarehouseDao warehouseDao = DaoFactory.getWarehouseDao(products);
        warehouseService = new EntityServiceImpl<>(warehouseDao);
    }

    public static EntityService<Product> getProductService() { return productService; }
    public static EntityService<Supplier> getSupplierService() { return supplierService; }
    public static EntityService<Warehouse> getWarehouseService() { return warehouseService; }
}

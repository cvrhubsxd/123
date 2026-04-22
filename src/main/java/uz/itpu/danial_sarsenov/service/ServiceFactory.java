package uz.itpu.danial_sarsenov.service;

import uz.itpu.danial_sarsenov.dao.*;
import uz.itpu.danial_sarsenov.entity.*;
import uz.itpu.danial_sarsenov.server.storage.ProductRepository;

import java.util.List;

public class ServiceFactory {

    private static EntityService<Product> productService;
    private static EntityService<Supplier> supplierService;
    private static EntityService<Warehouse> warehouseService;

    private ServiceFactory() {}

    public static void init() throws Exception {

        SupplierDao supplierDao = DaoFactory.getSupplierDao();
        supplierService = new EntityServiceImpl<>(supplierDao);

        List<Supplier> suppliers = supplierService.findAll();

        ProductDao productDao = DaoFactory.getProductDao(suppliers);
        productService = new EntityServiceImpl<>(productDao);

        List<Product> products = productService.findAll();

        // 🔥 IMPORTANT: SERVER STORAGE SYNC
        ProductRepository.init(products);

        WarehouseDao warehouseDao = DaoFactory.getWarehouseDao(products);
        warehouseService = new EntityServiceImpl<>(warehouseDao);
    }

    public static EntityService<Product> getProductService() {
        return productService;
    }

    public static EntityService<Supplier> getSupplierService() {
        return supplierService;
    }

    public static EntityService<Warehouse> getWarehouseService() {
        return warehouseService;
    }
}
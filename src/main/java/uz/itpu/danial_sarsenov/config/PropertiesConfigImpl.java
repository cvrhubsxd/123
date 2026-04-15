package uz.itpu.danial_sarsenov.config;

import uz.itpu.danial_sarsenov.controller.ControllerFactory;
import uz.itpu.danial_sarsenov.controller.storage.ProductStorage;
import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.service.ServiceFactory;
import uz.itpu.danial_sarsenov.view.ConsoleViewImpl;
import uz.itpu.danial_sarsenov.view.View;
import uz.itpu.danial_sarsenov.view.ViewFactory;

import java.util.List;

public class PropertiesConfigImpl implements Config {

    private final String propertiesName;

    public PropertiesConfigImpl(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    @Override
    public void init() {
        try {

            ServiceFactory.init();


            List<Product> products = ServiceFactory.getProductService().findAll();
            products.forEach(ProductStorage::add);


            ControllerFactory.init();


            View view = new ConsoleViewImpl();
            ViewFactory.init(view);

        } catch (Exception e) {
            throw new RuntimeException("Failed to init application", e);
        }
    }
}

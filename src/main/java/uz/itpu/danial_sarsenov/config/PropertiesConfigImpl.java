package uz.itpu.danial_sarsenov.config;

import uz.itpu.danial_sarsenov.controller.ControllerFactory;
import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.server.storage.ProductRepository;
import uz.itpu.danial_sarsenov.service.ServiceFactory;

import java.util.List;

public class PropertiesConfigImpl implements Config {

    private final String propertiesName;

    public PropertiesConfigImpl(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    @Override
    public void init() {
        try {
            AppConfig config = new AppConfig(propertiesName);

            ServiceFactory.init();

            List<Product> products = ServiceFactory.getProductService().findAll();
            ProductRepository.init(products);

            ControllerFactory.init();

            // сохраняем порт глобально
            System.setProperty("app.port", String.valueOf(config.getPort()));

        } catch (Exception e) {
            throw new RuntimeException("Failed to init application", e);
        }
    }
}
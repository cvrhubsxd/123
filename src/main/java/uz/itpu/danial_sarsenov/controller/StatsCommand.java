package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.controller.storage.ProductStorage;
import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.service.StatsService;

import java.util.Collection;

public class StatsCommand implements Command {

    private final StatsService statsService = new StatsService();

    @Override
    public Response execute(String[] args) {
        Collection<Product> products = ProductStorage.getAll();
        String stats = statsService.buildStats(products);
        return new ResponseImpl(stats, true, false);
    }
}

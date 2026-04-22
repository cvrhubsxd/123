package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.search.ProductSearchCriteria;
import uz.itpu.danial_sarsenov.exception.ServiceException;
import uz.itpu.danial_sarsenov.service.ProductSearchService;
import uz.itpu.danial_sarsenov.service.ServiceFactory;

import java.util.*;

public class FindCommand implements Command {

    private final ProductSearchService searchService = new ProductSearchService();

    @Override
    public Response execute(String[] args) {

        if (args == null || args.length < 2) {
            return new ResponseImpl("Usage: find ...", false, false);
        }

        String filter = args[1].toLowerCase();
        Map<String, String> params = parseParams(Arrays.copyOfRange(args, 2, args.length));

        String name = params.get("name");

        Double priceLow = null;
        Double priceHigh = null;

        if (params.containsKey("price")) {
            String[] range = params.get("price").split(";");
            priceLow = Double.parseDouble(range[0]);
            priceHigh = Double.parseDouble(range[1]);
        }

        ProductSearchCriteria criteria = switch (filter) {
            case "kitchen" -> new ProductSearchCriteria(name, "Kitchen", priceLow, priceHigh);
            case "electric" -> new ProductSearchCriteria(name, "Electric", priceLow, priceHigh);
            case "cooling" -> new ProductSearchCriteria(name, "Cooling", priceLow, priceHigh);
            default -> new ProductSearchCriteria(name, null, priceLow, priceHigh);
        };

        try {
            List<Product> products = ServiceFactory.getProductService().findAll();

            List<Product> result = searchService.search(products, criteria, name);

            if (result.isEmpty()) {
                return new ResponseImpl("No products found", true, false);
            }

            return new ResponseImpl(format(result), true, false);

        } catch (ServiceException e) {
            return new ResponseImpl("Service error", false, false);
        }
    }

    private Map<String, String> parseParams(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (String a : args) {
            String[] kv = a.split("=");
            if (kv.length == 2) map.put(kv[0], kv[1]);
        }
        return map;
    }

    private String format(List<Product> list) {
        StringBuilder sb = new StringBuilder();
        for (Product p : list) sb.append(p).append("\n");
        return sb.toString();
    }
}
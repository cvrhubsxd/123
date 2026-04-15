package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.entity.search.ProductSearchCriteria;
import uz.itpu.danial_sarsenov.exception.ServiceException;
import uz.itpu.danial_sarsenov.service.ServiceFactory;
import uz.itpu.danial_sarsenov.service.search.ProductSearchRanker;

import java.util.*;

public class FindCommand implements Command {

    private static final Set<String> SYSTEM_FILTERS =
            Set.of("all", "kitchen", "electric", "cooling");

    @Override
    public Response execute(String[] args) {
        if (args == null || args.length < 2) {
            return new ResponseImpl(
                    "Usage: find <all|kitchen|electric|cooling> [name=xxx] [price=low;high]",
                    false,
                    false
            );
        }

        String filter = args[1].toLowerCase();
        Map<String, String> params = parseParams(Arrays.copyOfRange(args, 2, args.length));

        String name = params.get("name");

        if (!SYSTEM_FILTERS.contains(filter)) {
            name = filter;
            filter = "all";
        }

        Double priceLow = null;
        Double priceHigh = null;

        if (params.containsKey("price")) {
            String[] range = params.get("price").split(";");
            try {
                if (range.length == 2) {
                    priceLow = Double.parseDouble(range[0]);
                    priceHigh = Double.parseDouble(range[1]);
                }
            } catch (NumberFormatException e) {
                return new ResponseImpl(
                        "Invalid price range format. Use low;high",
                        false,
                        false
                );
            }
        }

        ProductSearchCriteria criteria;

        switch (filter) {
            case "all" ->
                    criteria = new ProductSearchCriteria(name, null, priceLow, priceHigh, null);
            case "kitchen" ->
                    criteria = new ProductSearchCriteria(name, "Kitchen", priceLow, priceHigh, null);
            case "electric" ->
                    criteria = new ProductSearchCriteria(name, null, priceLow, priceHigh, "Electric");
            case "cooling" ->
                    criteria = new ProductSearchCriteria(name, null, priceLow, priceHigh, "Cooling");
            default ->
                    criteria = new ProductSearchCriteria(name, null, priceLow, priceHigh, null);
        }

        try {
            List<Product> products = ServiceFactory
                    .getProductService()
                    .find(criteria);
            final String searchName = name;

            if (searchName != null && !searchName.isBlank()) {
                products = products.stream()
                        .filter(p -> ProductSearchRanker.matches(p, searchName))
                        .sorted(ProductSearchRanker.comparator(searchName))
                        .toList();
            }


            return products.isEmpty()
                    ? new ResponseImpl("No appliances found", true, false)
                    : new ResponseImpl(formatList(products), true, false);

        } catch (ServiceException e) {
            return new ResponseImpl(
                    "Service error: " + e.getMessage(),
                    false,
                    true
            );
        }
    }

    private Map<String, String> parseParams(String[] paramStrings) {
        Map<String, String> map = new HashMap<>();
        for (String param : paramStrings) {
            String[] kv = param.split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0].toLowerCase(), kv[1]);
            }
        }
        return map;
    }

    private <T> String formatList(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (T item : list) {
            sb.append(item).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}

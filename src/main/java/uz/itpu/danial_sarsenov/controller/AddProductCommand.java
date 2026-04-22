package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.entity.Product;
import uz.itpu.danial_sarsenov.server.storage.ProductRepository;

public class AddProductCommand implements Command {

    @Override
    public Response execute(String[] args) {

        if (args.length != 5) {
            return new ResponseImpl(
                    "Usage: add_product id name category price quantity",
                    false,
                    false
            );
        }

        try {
            int id = Integer.parseInt(args[0]);
            String name = args[1];
            String category = args[2];
            double price = Double.parseDouble(args[3]);
            int quantity = Integer.parseInt(args[4]);

            Product product = new Product(
                    id, name, category, price, quantity, ""
            );

            ProductRepository.add(product);

            return new ResponseImpl("Product added: " + name, true, false);

        } catch (Exception e) {
            return new ResponseImpl("Invalid product data", false, false);
        }
    }
}
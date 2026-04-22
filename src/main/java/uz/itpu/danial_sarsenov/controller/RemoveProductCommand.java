package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.server.storage.ProductRepository;

public class RemoveProductCommand implements Command {

    @Override
    public Response execute(String[] args) {

        if (args.length != 1) {
            return new ResponseImpl("Usage: remove_product <id>", false, false);
        }

        try {
            int id = Integer.parseInt(args[0]);

            boolean removed = ProductRepository.remove(id);

            return new ResponseImpl(
                    removed ? "Product removed" : "Not found",
                    removed,
                    false
            );

        } catch (Exception e) {
            return new ResponseImpl("Invalid id", false, false);
        }
    }
}
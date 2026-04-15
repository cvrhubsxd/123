package uz.itpu.danial_sarsenov.dao;

import uz.itpu.danial_sarsenov.entity.Supplier;

public class SupplierDao extends FileBasedDao<Supplier> {

    public SupplierDao(String filePath) {
        super(filePath);
    }

    @Override
    protected Supplier parse(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new RuntimeException("Invalid supplier line: " + line);
        }
        return new Supplier(
                Integer.parseInt(parts[0].trim()),
                parts[1].trim(),
                parts[2].trim()
        );
    }
}

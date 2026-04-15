package uz.itpu.danial_sarsenov.dao;

import uz.itpu.danial_sarsenov.exception.DaoException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FileBasedDao<T> implements EntityDao<T> {

    protected final String filePath;

    protected FileBasedDao(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<T> find() throws DaoException {
        try {
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream(filePath);

            if (is == null) {
                throw new DaoException("File not found in resources: " + filePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines()
                        .filter(line -> !line.isBlank())
                        .map(this::parse)
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            throw new DaoException("Failed to read file: " + filePath, e);
        }
    }

    protected abstract T parse(String line);
}

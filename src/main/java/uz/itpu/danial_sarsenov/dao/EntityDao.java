package uz.itpu.danial_sarsenov.dao;

import java.util.List;

public interface EntityDao<T> {
    List<T> find();
}

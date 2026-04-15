package uz.itpu.danial_sarsenov.entity.search;

public interface SearchCriteria<T> {
    boolean matches(T entity);
}

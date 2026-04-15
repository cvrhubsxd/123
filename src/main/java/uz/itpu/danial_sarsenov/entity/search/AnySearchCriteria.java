package uz.itpu.danial_sarsenov.entity.search;

public class AnySearchCriteria<T> implements SearchCriteria<T> {
    @Override
    public boolean matches(T entity) {
        return true;
    }
}

package uz.itpu.danial_sarsenov.service;

import uz.itpu.danial_sarsenov.entity.BaseEntity;
import uz.itpu.danial_sarsenov.exception.ServiceException;
import uz.itpu.danial_sarsenov.entity.search.SearchCriteria;
import java.util.List;

public interface EntityService<T> {
    List<T> find(SearchCriteria<? super T> criteria) throws ServiceException;
    List<T> findAll() throws ServiceException;
}

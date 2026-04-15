package uz.itpu.danial_sarsenov.service;

import uz.itpu.danial_sarsenov.dao.EntityDao;
import uz.itpu.danial_sarsenov.entity.BaseEntity;
import uz.itpu.danial_sarsenov.entity.search.SearchCriteria;
import uz.itpu.danial_sarsenov.exception.ServiceException;

import java.util.List;

public class EntityServiceImpl<T extends BaseEntity> implements EntityService<T> {

    private final EntityDao<T> dao;

    public EntityServiceImpl(EntityDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public List<T> find(SearchCriteria<? super T> criteria) throws ServiceException {
        try {
            return dao.find()
                    .stream()
                    .filter(criteria::matches)
                    .toList();
        } catch (Exception e) {
            throw new ServiceException("Failed to find entities", e);
        }
    }

    @Override
    public List<T> findAll() throws ServiceException {
        try {
            return dao.find();
        } catch (Exception e) {
            throw new ServiceException("Failed to find all entities", e);
        }
    }
}

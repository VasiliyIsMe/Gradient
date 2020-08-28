package com.dityatkin.gradient.dao;

import java.util.List;

public interface CrudDao<E, T> {

    void saveToTable(E entity);

    List<T> findAll();

    void deleteAll();
}

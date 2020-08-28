package com.dityatkin.gradient.dao.impl;

import com.dityatkin.gradient.dao.CrudDao;
import com.dityatkin.gradient.dao.DBConnector;
import com.dityatkin.gradient.dao.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCrudDaoImpl<E, T> implements CrudDao<E, T> {
    private final DBConnector dbConnector;
    private final String truncateTableQuery;
    private final String insertQuery;
    private final String showTableQuery;

    public AbstractCrudDaoImpl(DBConnector dbConnector, String insertQuery, String truncateTableQuery, String showTableQuery) {
        this.dbConnector = dbConnector;
        this.insertQuery = insertQuery;
        this.showTableQuery = showTableQuery;
        this.truncateTableQuery = truncateTableQuery;
    }

    @Override
    public void saveToTable(E entity) {
        try(Connection connection = dbConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            connection.setAutoCommit(false);
            insert(statement, entity);
            connection.commit();
        }catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion failed.", e);
        }
    }

    @Override
    public void deleteAll() {
        try(Connection connection = dbConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(truncateTableQuery);
            statement.executeUpdate();
        }catch (SQLException e ) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public List<T> findAll() {

        try (Connection connection = dbConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(showTableQuery);

            try (final ResultSet resultSet = statement.executeQuery()) {
                List<T> entities = new ArrayList<>();

                while (resultSet.next()) {
                    entities.add(mapResSetToEntity(resultSet));
                }

                return entities;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    protected abstract void insert(PreparedStatement statement, E entity) throws SQLException;

    protected abstract T mapResSetToEntity(ResultSet resultSet) throws SQLException;
}

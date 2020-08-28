package com.dityatkin.gradient.dao.impl;

import com.dityatkin.gradient.dao.CrudDao;
import com.dityatkin.gradient.dao.DBConnector;
import com.dityatkin.gradient.domain.FileEntity;
import com.dityatkin.gradient.domain.FileInfoMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilesCrudDao extends AbstractCrudDaoImpl<FileEntity, FileInfoMapper>
                                                                       implements CrudDao<FileEntity, FileInfoMapper> {
    private static final String TRUNCATE_TABLE_QUERY = "truncate table gradient.files";
    private static final String SHOW_TABLE_QUERY = "SELECT * FROM gradient.files";
    private static final String INSERT_QUERY =
            "INSERT INTO gradient.files(file_name, file_size, from_path, actual_path, file_type) values(?,?,?,?,?)";

    public FilesCrudDao(DBConnector connector) {
        super(connector, INSERT_QUERY, TRUNCATE_TABLE_QUERY, SHOW_TABLE_QUERY);
    }

    @Override
    protected void insert(PreparedStatement statement, FileEntity entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setDouble(2, entity.getFileSize());
        statement.setString(3, entity.getRootPath());
        statement.setString(4, entity.getActualPath());
        statement.setString(5, entity.getFileType());
        statement.executeUpdate();
    }

    @Override
    protected FileInfoMapper mapResSetToEntity(ResultSet resultSet) throws SQLException {
        return FileInfoMapper.builder()
                            .withName(resultSet.getString("file_name"))
                            .withSize((int)resultSet.getDouble("file_size"))
                            .withPath(resultSet.getString("actual_path"))
                            .withRootPath(resultSet.getString("from_path"))
                            .withFileType(resultSet.getString("file_type"))
                            .build();
    }
}

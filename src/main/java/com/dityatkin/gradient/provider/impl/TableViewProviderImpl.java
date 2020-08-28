package com.dityatkin.gradient.provider.impl;

import com.dityatkin.gradient.dao.DBConnector;
import com.dityatkin.gradient.dao.impl.FilesCrudDao;
import com.dityatkin.gradient.domain.FileInfoMapper;
import com.dityatkin.gradient.provider.TableViewProvider;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

public class TableViewProviderImpl implements TableViewProvider {

    public void createTable() {
        final FilesCrudDao filesCrudDao = new FilesCrudDao(new DBConnector("database.properties"));

        final JFrame tableFrame = new JFrame("result of transfer");
        final List<FileInfoMapper> filesInfo = filesCrudDao.findAll();

        final JTable table = new JTable(getTableModel(filesInfo));
        final JScrollPane pane = new JScrollPane(table);
        tableFrame.getContentPane().add(pane);
        tableFrame.setPreferredSize(new Dimension(850, 500));
        tableFrame.pack();
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }

    private DefaultTableModel getTableModel(List<FileInfoMapper> files) {
        final Vector<String> columns = new Vector<>();
        columns.add("file name");
        columns.add("file size, Mb");
        columns.add("path from");
        columns.add("actual path");
        columns.add("file type");

        final Vector<Vector<Object>> tableRaws = new Vector<>();

        files.stream().forEach(n -> {Vector<Object> raw = new Vector<>();
                                     raw.add(n.getName());
                                     raw.add(n.getFileSize());
                                     raw.add(n.getRootPath());
                                     raw.add(n.getActualPath());
                                     raw.add(n.getFileType());

                                     tableRaws.add(raw);
                                    });

        return new DefaultTableModel(tableRaws, columns);
    }
}

package com.dityatkin.gradient.provider;

import com.dityatkin.gradient.domain.FileMovingConfiguration;
import com.dityatkin.gradient.userInterface.ProvideWindowManipulations;

import static javax.swing.SwingUtilities.invokeLater;

public class FileOperationsProvider {
    private final FileMoveProvider<FileMovingConfiguration> fileMoveProvider;
    private final TableViewProvider tableViewProvider;

    public FileOperationsProvider(FileMoveProvider<FileMovingConfiguration> fileMoveProvider,
                                  TableViewProvider tableViewProvider) {
        this.fileMoveProvider = fileMoveProvider;
        this.tableViewProvider = tableViewProvider;
    }

    public void provideMoveFiles() {
        invokeLater( () -> new ProvideWindowManipulations(fileMoveProvider, tableViewProvider));
    }
}

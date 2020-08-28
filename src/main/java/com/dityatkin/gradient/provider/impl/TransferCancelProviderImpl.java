package com.dityatkin.gradient.provider.impl;

import com.dityatkin.gradient.domain.FileMovingConfiguration;
import com.dityatkin.gradient.provider.FileMoveProvider;
import com.dityatkin.gradient.provider.TransferCancelProvider;

import java.nio.file.Path;
import java.util.Map;

public class TransferCancelProviderImpl implements TransferCancelProvider {
    private final FileMovingConfiguration filesConfiguration;
    private final FileMoveProvider<FileMovingConfiguration> fileMoveProviderImpl;

    public TransferCancelProviderImpl(FileMovingConfiguration filesConfiguration,
                                      FileMoveProvider<FileMovingConfiguration> fileMoveProviderImpl) {
        this.filesConfiguration = filesConfiguration;
        this.fileMoveProviderImpl = fileMoveProviderImpl;
    }

    public void cancelFileTransfer() {
        System.out.println("cancel operation");
        filesConfiguration.getFileTypeToFilePath().entrySet().stream().forEach(n ->
                fileMoveProviderImpl.sortFilesToDirectories(
                        makeFileCancelMoveConfiguration(n, filesConfiguration.getRootPath())));
    }

    private FileMovingConfiguration makeFileCancelMoveConfiguration(Map.Entry<String, Path> entry, Path path) {
        FileMovingConfiguration newConfiguration = new FileMovingConfiguration(entry.getValue());
        newConfiguration.addFileTypeToFilePath(entry.getKey(), path);

        return newConfiguration;
    }

}

package com.dityatkin.gradient.provider.impl;

import com.dityatkin.gradient.dao.DBConnector;
import com.dityatkin.gradient.dao.impl.FilesCrudDao;
import com.dityatkin.gradient.domain.FileEntity;
import com.dityatkin.gradient.domain.FileMovingConfiguration;
import com.dityatkin.gradient.provider.FileMoveProvider;
import com.dityatkin.gradient.service.writer.ProvideFileWrite;
import com.dityatkin.gradient.service.reader.ProvideFileRead;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;

import static com.dityatkin.gradient.userInterface.ProvideWindowManipulations.*;
import static java.nio.file.Files.delete;

public class FileMoveProviderImpl implements FileMoveProvider<FileMovingConfiguration> {
    private static final String PATH_DELIMITER = "\\";
    private static final String DOT = ".";
    private static final int MAX_THREAD_COUNT = 3;

    private final Semaphore semaphore = new Semaphore(MAX_THREAD_COUNT);
    private final ProvideFileRead<Path, FileEntity> provideFileRead;
    private final ProvideFileWrite<FileEntity> provideFileWrite;
    private final FilesCrudDao filesCrudDao = new FilesCrudDao(new DBConnector("database.properties"));

    public FileMoveProviderImpl(ProvideFileRead<Path, FileEntity> provideFileRead,
                                ProvideFileWrite<FileEntity> ProvideFileWrite) {
        this.provideFileRead = provideFileRead;
        this.provideFileWrite = ProvideFileWrite;
    }

    public void sortFilesToDirectories(FileMovingConfiguration filesConfiguration) {
        System.out.println("sortFilesToDirectories from: " + filesConfiguration.getRootPath());

        try {
            Files.list(filesConfiguration.getRootPath()).forEach(n -> {
                Path kindOfOutputDir = directorySelect(filesConfiguration, n);

                if (kindOfOutputDir != null) {
                    replaceFile(kindOfOutputDir, n);
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Path directorySelect(FileMovingConfiguration filesConfiguration, Path filePath) {
        System.out.println("directorySelect");
        Path kindOfOutputDir = null;
        final String fileType = getFileType(filePath);

        switch (fileType) {
            case MP3_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(MP3_TYPE);
                break;

            case EXE_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(EXE_TYPE);
                break;

            case AVI_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(AVI_TYPE);
                break;

            case MP4_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(MP4_TYPE);
                break;

            case TORRENT_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(TORRENT_TYPE);
                break;

            case DOCX_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(DOCX_TYPE);
                break;

            case DOC_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(DOC_TYPE);
                break;

            case PDF_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(PDF_TYPE);
                break;

            case TXT_TYPE:
                kindOfOutputDir = filesConfiguration.getFileTypeToFilePath().get(PDF_TYPE);
        }

        return kindOfOutputDir;
    }

    private String getFileType(Path filePath) {
        return filePath.toString().substring(filePath.toString().lastIndexOf(DOT) + 1);
    }

    private void replaceFile(Path newDirectory, Path filePath) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
        System.out.println("replaceFile");
        System.out.println("from root " + filePath.toString());

        final Path newFilePath = createFilePath(newDirectory, filePath);
        final FileEntity fileEntity = provideFileRead.read(filePath);
        fileEntity.setPath(newFilePath);
        provideFileWrite.write(fileEntity);

        filesCrudDao.saveToTable(fileEntity);

        try {
            delete(filePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            semaphore.release();
        }
    }

    private Path createFilePath(Path newPath, Path oldFilePath) {
        return Paths.get(newPath + PATH_DELIMITER + oldFilePath.toFile().getName());
    }
}


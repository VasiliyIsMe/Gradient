package com.dityatkin.gradient.service.reader;

import com.dityatkin.gradient.domain.FileEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class ProvideFileReadImpl implements ProvideFileRead<Path, FileEntity> {

    @Override
    public FileEntity read(Path rootPath) {
        final byte[] bytesOfFile = new byte[(int) rootPath.toFile().length()];

        try (FileInputStream inputStream = new FileInputStream(rootPath.toFile())) {
            inputStream.read(bytesOfFile);

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    return FileEntity.builder()
                     .withBytes(bytesOfFile)
                     .withRootPath(rootPath)
                     .build();
    }
}

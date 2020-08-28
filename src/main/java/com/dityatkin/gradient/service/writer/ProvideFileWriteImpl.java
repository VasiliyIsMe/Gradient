package com.dityatkin.gradient.service.writer;

import com.dityatkin.gradient.domain.FileEntity;

import java.io.FileOutputStream;
import java.io.IOException;

public class ProvideFileWriteImpl implements ProvideFileWrite<FileEntity> {

    @Override
    public void write(FileEntity entity) {

        try (FileOutputStream outputStream = new FileOutputStream(entity.getPath().toFile())) {
            if (entity.getFileBytes().length > 0) {
                outputStream.write(entity.getFileBytes(), 0, entity.getFileBytes().length);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

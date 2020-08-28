package com.dityatkin.gradient.service.writer;

import com.dityatkin.gradient.domain.FileEntity;
import com.dityatkin.gradient.service.reader.ProvideFileRead;
import com.dityatkin.gradient.service.reader.ProvideFileReadImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

class ProvideFileWriteImplTest {
    private final ProvideFileWrite<FileEntity> fileWrite = new ProvideFileWriteImpl();
    private final ProvideFileRead<Path, FileEntity> fileRead = new ProvideFileReadImpl();

    @Test
    void writeShouldWriteBytesToTheCreatedFile() {
        final String line = "line for bytes";
        final byte[] bytes = line.getBytes();
        Path path = Paths.get("src/test/resources/fileWriteTestSource.test");
        FileEntity fileEntity = FileEntity.builder()
                                          .withRootPath(Paths.get("some root path"))
                                          .withBytes(bytes)
                                          .build();
        fileEntity.setPath(path);
        fileWrite.write(fileEntity);

        assertThat(fileRead.read(path).getFileBytes(), equalTo(bytes));
    }
}
package com.dityatkin.gradient.service.reader;

import com.dityatkin.gradient.domain.FileEntity;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

class ProvideFileReadImplTest {
    ProvideFileRead<Path, FileEntity> provideFileRead = new ProvideFileReadImpl();

    @Test
    void provideFileReadShouldReturnFileEntityWithNonZeroSize() {
        final String fileLine = "12314235135141231243";
        byte[] expectedBytes = fileLine.getBytes();
        final FileEntity entity = provideFileRead.read(Paths.get("src/test/resources/fileReadTestSource.test"));

        assertThat(entity.getFileBytes(), equalTo(expectedBytes));
    }
}
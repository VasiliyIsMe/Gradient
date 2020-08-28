package com.dityatkin.gradient.provider.impl;

import com.dityatkin.gradient.domain.FileEntity;
import com.dityatkin.gradient.domain.FileMovingConfiguration;
import com.dityatkin.gradient.provider.FileMoveProvider;
import com.dityatkin.gradient.service.reader.ProvideFileRead;
import com.dityatkin.gradient.service.reader.ProvideFileReadImpl;
import com.dityatkin.gradient.service.writer.ProvideFileWrite;
import com.dityatkin.gradient.service.writer.ProvideFileWriteImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

class FileMoveProviderImplTest {
    public static final String DOC_TYPE = "doc";
    public static final String DOCX_TYPE = "docx";
    public static final String TXT_TYPE = "txt";
    public static final String PDF_TYPE = "pdf";
    public static final String MP3_TYPE = "mp3";
    public static final String MP4_TYPE = "mp4";
    public static final String AVI_TYPE = "avi";
    public static final String EXE_TYPE = "exe";
    public static final String TORRENT_TYPE = "torrent";

    private final ProvideFileRead<Path, FileEntity> fileReader = new ProvideFileReadImpl();
    private final ProvideFileWrite<FileEntity> fileWriter = new ProvideFileWriteImpl();
    private final FileMoveProvider<FileMovingConfiguration> fileMoveProvider =
                                                                    new FileMoveProviderImpl(fileReader, fileWriter);

    @Test void sortFilesToDirectoriesShouldSelectCorrectDirectoryForMusic() {
        final boolean expected = true;
        final FileMovingConfiguration configuration = new FileMovingConfiguration(
                                                           Paths.get("src/test/resources/fileMoveTest/pathFrom"));
        configuration.addFileTypeToFilePath(MP3_TYPE, Paths.get("src/test/resources/fileMoveTest/pathTo/music"));
        fileMoveProvider.sortFilesToDirectories(configuration);
        final boolean isPresent = Paths.get("src/test/resources/fileMoveTest/pathTo/music/mp3.mp3")
                                                                                                .toFile().isFile();
        assertThat(isPresent, equalTo(expected));
    }

    @Test void sortFilesToDirectoriesShouldSelectCorrectDirectoryForVideo() {
        final boolean expected = true;
        final FileMovingConfiguration configuration = new FileMovingConfiguration(
                                                          Paths.get("src/test/resources/fileMoveTest/pathFrom"));
        configuration.addFileTypeToFilePath(MP4_TYPE, Paths.get("src/test/resources/fileMoveTest/pathTo/video"));
        configuration.addFileTypeToFilePath(AVI_TYPE, Paths.get("src/test/resources/fileMoveTest/pathTo/video"));
        fileMoveProvider.sortFilesToDirectories(configuration);
        final boolean isPresent = Paths.get("src/test/resources/fileMoveTest/pathTo/video/mp4.mp4")
                                                                                        .toFile().isFile() &&
                            Paths.get("src/test/resources/fileMoveTest/pathTo/video/avi.avi")
                                                                                        .toFile().isFile();
        assertThat(isPresent, equalTo(expected));
    }

    @Test void sortFilesToDirectoriesShouldSelectCorrectDirectoryForDocuments() {
        final boolean expected = true;
        final FileMovingConfiguration configuration = new FileMovingConfiguration(
                Paths.get("src/test/resources/fileMoveTest/pathFrom"));
        configuration.addFileTypeToFilePath(DOC_TYPE,
                                                   Paths.get("src/test/resources/fileMoveTest/pathTo/documents"));
        configuration.addFileTypeToFilePath(TXT_TYPE,
                                                   Paths.get("src/test/resources/fileMoveTest/pathTo/documents"));
        configuration.addFileTypeToFilePath(DOCX_TYPE,
                                                   Paths.get("src/test/resources/fileMoveTest/pathTo/documents"));
        configuration.addFileTypeToFilePath(PDF_TYPE,
                                                   Paths.get("src/test/resources/fileMoveTest/pathTo/documents"));
        fileMoveProvider.sortFilesToDirectories(configuration);
        final boolean isPresent = Paths.get("src/test/resources/fileMoveTest/pathTo/documents/pdf.pdf")
                                                                                                .toFile().isFile() &&
                            Paths.get("src/test/resources/fileMoveTest/pathTo/documents/doc.doc")
                                                                                                .toFile().isFile() &&
                            Paths.get("src/test/resources/fileMoveTest/pathTo/documents/docx.docx")
                                                                                                .toFile().isFile() &&
                            Paths.get("src/test/resources/fileMoveTest/pathTo/documents/txt.txt")
                                                                                                .toFile().isFile();
        assertThat(isPresent, equalTo(expected));
    }

    @Test void sortFilesToDirectoriesShouldSelectCorrectDirectoryForExecutableFiles() {
        final boolean expected = true;
        final FileMovingConfiguration configuration = new FileMovingConfiguration(
                                                Paths.get("src/test/resources/fileMoveTest/pathFrom"));
        configuration.addFileTypeToFilePath(EXE_TYPE,
                                                Paths.get("src/test/resources/fileMoveTest/pathTo/executables"));
        fileMoveProvider.sortFilesToDirectories(configuration);
        final boolean isPresent = Paths.get("src/test/resources/fileMoveTest/pathTo/executables/exe.exe")
                                                                                                 .toFile().isFile();
        assertThat(isPresent, equalTo(expected));
    }

    @Test void sortFilesToDirectoriesShouldSelectCorrectDirectoryForTorrents() {
        final boolean expected = true;
        final FileMovingConfiguration configuration = new FileMovingConfiguration(
                                                     Paths.get("src/test/resources/fileMoveTest/pathFrom"));
        configuration.addFileTypeToFilePath(TORRENT_TYPE,
                                                     Paths.get("src/test/resources/fileMoveTest/pathTo/torrents"));
        fileMoveProvider.sortFilesToDirectories(configuration);
        final boolean isPresent = Paths.get("src/test/resources/fileMoveTest/pathTo/torrents/torrent.torrent")
                                                                                                  .toFile().isFile();
        assertThat(isPresent, equalTo(expected));
    }
}

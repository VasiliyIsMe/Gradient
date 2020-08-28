package com.dityatkin.gradient;

import com.dityatkin.gradient.domain.FileEntity;
import com.dityatkin.gradient.domain.FileMovingConfiguration;
import com.dityatkin.gradient.provider.FileMoveProvider;
import com.dityatkin.gradient.provider.impl.FileMoveProviderImpl;
import com.dityatkin.gradient.provider.FileOperationsProvider;
import com.dityatkin.gradient.provider.TableViewProvider;
import com.dityatkin.gradient.provider.impl.TableViewProviderImpl;
import com.dityatkin.gradient.service.reader.ProvideFileRead;
import com.dityatkin.gradient.service.reader.ProvideFileReadImpl;
import com.dityatkin.gradient.service.writer.ProvideFileWrite;
import com.dityatkin.gradient.service.writer.ProvideFileWriteImpl;

import java.nio.file.Path;

public class GradientApplication {
    public static void main(String[] args) {
        final ProvideFileRead<Path, FileEntity> provideFileRead = new ProvideFileReadImpl();
        final ProvideFileWrite<FileEntity> provideFileWrite = new ProvideFileWriteImpl();
        final TableViewProvider tableViewProvider = new TableViewProviderImpl();
        final FileMoveProvider<FileMovingConfiguration> fileMoveProvider = new FileMoveProviderImpl(provideFileRead,
                                                                                                    provideFileWrite);

        FileOperationsProvider fileOperationsProvider = new FileOperationsProvider(fileMoveProvider, tableViewProvider);
        fileOperationsProvider.provideMoveFiles();
    }
}

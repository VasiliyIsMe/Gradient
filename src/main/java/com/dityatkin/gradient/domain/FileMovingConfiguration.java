package com.dityatkin.gradient.domain;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileMovingConfiguration {
    private final Map<String, Path> fileTypeToFilePath;
    private final Path rootPath;

    public FileMovingConfiguration(Path rootPath) {
        fileTypeToFilePath = new HashMap<>();
        this.rootPath = rootPath;
    }

    public void addFileTypeToFilePath(String type, Path path) {
        fileTypeToFilePath.put(type, path);
    }

    public Map<String, Path> getFileTypeToFilePath() {
        return fileTypeToFilePath;
    }

    public Path getRootPath() {
        return rootPath;
    }
}


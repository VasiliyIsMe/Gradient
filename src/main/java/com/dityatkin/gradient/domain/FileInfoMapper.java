package com.dityatkin.gradient.domain;

import java.util.Objects;

public class FileInfoMapper {
    private final int fileSize;
    private final String path;
    private final String rootPath;
    private final String fileName;
    private final String fileType;

    private FileInfoMapper(Builder builder) {
        this.fileSize = builder.fileSize;
        this.path = builder.path;
        this.rootPath = builder.rootPath;
        this.fileName = builder.fileName;
        this.fileType = builder.fileType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getActualPath() {
        return path;
    }

    public String getName() {
        return fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return "file: " + fileName + "; size: " + fileSize + " Mbytes.";
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileSize, path);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileInfoMapper other = (FileInfoMapper) o;

        return this.path.equals(other.path) &&
                Objects.equals(this.fileSize, other.fileSize) && this.fileName.equals(other.fileName);
    }

    public static class Builder {
        private String fileName;
        private int fileSize;
        private String path;
        private String rootPath;
        private String fileType;

        private Builder() {
        }

        public Builder withSize(int fileBytes) {
            this.fileSize = fileBytes;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public Builder withRootPath(String path) {
            this.rootPath = path;
            return this;
        }

        public Builder withName(String name) {
            this.fileName = name;
            return this;
        }

        public Builder withFileType(String type) {
            this.fileType = type;
            return this;
        }

        public FileInfoMapper build() {
            return new FileInfoMapper(this);
        }
    }

}

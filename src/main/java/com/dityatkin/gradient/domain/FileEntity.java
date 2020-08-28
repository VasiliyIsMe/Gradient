package com.dityatkin.gradient.domain;

import java.nio.file.Path;
import java.util.Objects;

public class FileEntity {
    private static Integer BEGIN_INDEX = 0;
    private static final Character DOT_CHARACTER = '.';
    private static final String SLASH_CHARACTER = "\\";
    private final byte[] fileBytes;
    private final Path rootPath;
    private Path path;

    private FileEntity(Builder builder) {
        this.fileBytes = builder.fileBytes;
        this.path = builder.path;
        this.rootPath = builder.rootPath;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public static Builder builder() {
        return new Builder();
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public Path getPath() {
        return path;
    }

    public String getActualPath() {
        return path.toString().substring(BEGIN_INDEX, path.toString().lastIndexOf(SLASH_CHARACTER));
    }

    public String getName() {
        return path.toString().substring(path.toString().lastIndexOf(SLASH_CHARACTER) + 1);
    }

    public int getFileSize() {
        return fileBytes.length / (int) Math.pow(10.0, 6.0);
    }

    public String getRootPath() {
        return rootPath.toString().substring(BEGIN_INDEX, rootPath.toString().lastIndexOf(SLASH_CHARACTER));
    }

    public String getFileType() {
        return rootPath.toString().substring(rootPath.toString().lastIndexOf(DOT_CHARACTER) + 1);
    }

    @Override
    public String toString() {
        return "file: " + path.toString() + "; size: " + (fileBytes.length / (int) Math.pow(10, 6)) + " Mbytes.";
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileBytes, path);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileEntity other = (FileEntity) o;

        return this.path.toString().equals(other.path.toString()) &&
                Objects.equals(this.fileBytes, other.fileBytes);
    }

    public static class Builder {
        private byte[] fileBytes;
        private Path path;
        private Path rootPath;

        private Builder() {
        }

        public Builder withBytes(byte[] fileBytes) {
            this.fileBytes = fileBytes;
            return this;
        }

        public Builder withPath(Path path) {
            this.path = path;
            return this;
        }

        public Builder withRootPath(Path path) {
            this.rootPath = path;
            return this;
        }

        public FileEntity build() {
            return new FileEntity(this);
        }
    }
}

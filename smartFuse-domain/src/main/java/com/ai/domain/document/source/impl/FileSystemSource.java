package com.ai.domain.document.source.impl;

import com.ai.domain.document.Metadata;
import com.ai.domain.document.source.DocumentSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ai.domain.document.Document.ABSOLUTE_DIRECTORY_PATH;
import static com.ai.domain.document.Document.FILE_NAME;


public class FileSystemSource implements DocumentSource {

    public final Path path;

    public FileSystemSource(Path path) {
        this.path = path;
    }

    public static FileSystemSource from(Path filePath) {
        return new FileSystemSource(filePath);
    }

    public static FileSystemSource from(String filePath) {
        return new FileSystemSource(Paths.get(filePath));
    }

    public static FileSystemSource from(URI fileUri) {
        return new FileSystemSource(Paths.get(fileUri));
    }

    public static FileSystemSource from(File file) {
        return new FileSystemSource(file.toPath());
    }

    @Override
    public InputStream inputStream() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public Metadata metadata() {
        return new Metadata()
                .add(FILE_NAME, path.getFileName())
                .add(ABSOLUTE_DIRECTORY_PATH, path.getParent().toAbsolutePath());
    }
}

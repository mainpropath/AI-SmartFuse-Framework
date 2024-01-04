package com.ai.domain.document;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.ai.common.util.Exceptions.illegalArgument;
import static com.ai.domain.document.DocumentLoaderUtils.parserFor;
import static com.ai.domain.document.source.impl.FileSystemSource.from;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isRegularFile;

@Slf4j
public class FileSystemDocumentLoader {

    public static Document loadDocument(Path filePath) {
        return loadDocument(filePath, DocumentType.of(filePath.toString()));
    }

    public static Document loadDocument(String filePath) {
        return loadDocument(Paths.get(filePath));
    }

    public static Document loadDocument(Path filePath, DocumentType documentType) {
        if (!isRegularFile(filePath)) {
            throw illegalArgument("%s is not a file", filePath);
        }

        return DocumentLoaderUtils.load(from(filePath), parserFor(documentType));
    }

    public static Document loadDocument(String filePath, DocumentType documentType) {
        return loadDocument(Paths.get(filePath), documentType);
    }

    public static List<Document> loadDocuments(Path directoryPath) {
        if (!isDirectory(directoryPath)) {
            throw illegalArgument("%s is not a directory", directoryPath);
        }

        List<Document> documents = new ArrayList<>();

        try (Stream<Path> paths = Files.list(directoryPath)) {
            paths.filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        try {
                            Document document = loadDocument(filePath);
                            documents.add(document);
                        } catch (Exception e) {
                            log.warn("Failed to load document from " + filePath, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return documents;
    }

    public static List<Document> loadDocuments(String directoryPath) {
        return loadDocuments(Paths.get(directoryPath));
    }
}

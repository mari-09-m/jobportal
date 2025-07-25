package com.mweb.jobportal.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {

    public Resource getFileAsResourse(String downloadDir, String fileName) throws IOException {
        Path directoryPath = Paths.get(downloadDir);

        if (!Files.exists(directoryPath)) {
            return null;
        }

        Path foundFile = null;
        for (Path file : Files.list(directoryPath).toList()) {
            if (file.getFileName().toString().equalsIgnoreCase(fileName)) {
                foundFile = file;
                break;
            }
        }

        if (foundFile != null && Files.exists(foundFile)) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }
}

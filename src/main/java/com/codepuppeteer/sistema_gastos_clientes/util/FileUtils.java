package com.codepuppeteer.sistema_gastos_clientes.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private FileUtils() {}

    public static void createDirectoryIfNotExists(String directory) throws IOException {
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public static boolean deleteFile(String pathStr) {
        File file = new File(pathStr);
        return file.exists() && file.delete();
    }

    public static boolean fileExists(String pathStr) {
        File file = new File(pathStr);
        return file.exists();
    }
}

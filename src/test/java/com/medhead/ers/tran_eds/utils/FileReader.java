package com.medhead.ers.tran_eds.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public static String readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllLines(path).get(0);
    }
}

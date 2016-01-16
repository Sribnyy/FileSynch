package com.juja.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        String source = "C:\\source\\";
        String destination = "C:\\destination\\";
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        try {
            validateInputParameter(sourcePath, destinationPath);
            FileSynchInfoImpl fileSynchInfo = new FileSynchInfoImpl(sourcePath, destinationPath);
            System.out.println("Relative path and size files:");
            fileSynchInfo.printFilesSize(sourcePath);
            System.out.println("-------------------------------------------------------------");
            Path[] result = fileSynchInfo.fetchFileToBeCopy();
            System.out.println("Fetch files to be copy:");
            System.out.println(Arrays.toString(result));
        }catch(RuntimeException e){
            System.err.println(e.getMessage());

        }
    }

    public static void validateInputParameter(Path source, Path destination){
        if (!Files.isDirectory(source)){
            throw new RuntimeException("Not found directory: " + source.toString());
        }
        if (!Files.isDirectory(destination)) {
            try {
                Files.createDirectory(destination);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create the directory: " + destination.toString());
            }
        }

    }
}


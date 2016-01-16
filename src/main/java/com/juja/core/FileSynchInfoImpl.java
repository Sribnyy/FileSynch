package com.juja.core;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.util.Arrays;

public class FileSynchInfoImpl implements FileSynchInfo{

    private Path source;
    private Path destination;

    public FileSynchInfoImpl(Path source, Path destination){
        this.source = source;
        this.destination = destination;
    }

    private void bypassingFileTree(Path path, FileVisitor fileVisitor){
        try {
            Files.walkFileTree(path, fileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GetFileInfoStrategy.FileInformation[] getFilesSize(GetFileInfoStrategy.FileInformation[] fileInform,
                                                               Path path){
        GetFileInfoStrategy getFilesSize = new GetFileInfoStrategy(fileInform, path);
        bypassingFileTree(path, getFilesSize);
        return getFilesSize.getFileInform();

    }

    public void printFilesSize(Path path){
        GetFileInfoStrategy.FileInformation[] fileInform = new GetFileInfoStrategy.FileInformation[100];
        System.out.println(Arrays.toString(getFilesSize(fileInform, path)));

    }

    @Override
    public Path[] fetchFileToBeCopy() {
        GetFileInfoStrategy.FileInformation[] fileInformSource = new GetFileInfoStrategy.FileInformation[100];
        GetFileInfoStrategy.FileInformation[] fileInformDestination = new GetFileInfoStrategy.FileInformation[100];
        Path[] result = new Path[100];
        fileInformSource = getFilesSize(fileInformSource, source);
        fileInformDestination = getFilesSize(fileInformDestination, destination);
        int index = 0;
        for (GetFileInfoStrategy.FileInformation file : fileInformSource){
            if (fileInformDestination.length == 0){
                result[index++] = file.getPath();
            } else {
                for (int i = 0; i < fileInformDestination.length; i++) {
                    if (file.equals(fileInformDestination[i])) {
                        break;
                    }else{
                        if (i == fileInformDestination.length - 1){
                            result[index++] = file.getPath();
                        } else {/*do nothing*/}
                    }
                }
            }
        }
        Path[] copyResult = Arrays.copyOf(result, index);
        return copyResult;
    }

}

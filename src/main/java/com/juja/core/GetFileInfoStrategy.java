package com.juja.core;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;


public class GetFileInfoStrategy extends SimpleFileVisitor<Path> {
    private Path path;
    private FileInformation[] fileInform;
    private int index = 0;

    public class FileInformation{
        private Path path;
        private long size;

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FileInformation other = (FileInformation) obj;
            if (!this.path.equals(other.path) || other.path == null) {
                return false;
            }
            if (this.size != other.size) {
                return false;
            }
            return true;
        }

        public Path getPath(){
            return path;
        }

        public long getSize(){
            return size;
        }

        @Override
        public String toString(){
            return "\n{" + path.toString() + ", " + size + " byte}";
        }
    }

    public GetFileInfoStrategy(FileInformation[] fileInform, Path path){
        this.fileInform = fileInform;
        this.path = path;

    }

    @Override
    public FileVisitResult visitFile(Path path,
                                     BasicFileAttributes fileAttributes) {
        FileInformation fileInformation = new FileInformation();
        fileInformation.path = this.path.relativize(path);
        fileInformation.size = fileAttributes.size();
        fileInform[index++] = fileInformation;
        return FileVisitResult.CONTINUE;
    }

    public FileInformation[] getFileInform(){
        FileInformation[] copyFileInform = Arrays.copyOf(fileInform, index);
        return copyFileInform;
    }
}

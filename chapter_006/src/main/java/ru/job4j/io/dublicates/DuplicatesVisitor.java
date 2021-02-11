package ru.job4j.io.dublicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file.toAbsolutePath());
        return super.visitFile(file, attrs);
    }

    public static class DublicatesFinder {
        public static void main(String[] args) throws IOException {
            Files.walkFileTree(Path.of("./"), new DuplicatesVisitor());
        }
    }
}

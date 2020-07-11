package net.simon987.tika_test;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;

public class FileVisitor extends SimpleFileVisitor<Path> {

    private final ExecutorService es;

    public FileVisitor(ExecutorService es) {
        this.es = es;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        if (attr.isRegularFile()) {
            ParseTask task = new ParseTask(file.toString());
            es.submit(task);
        }

        return FileVisitResult.CONTINUE;
    }
}
package net.simon987.tika_test;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(4);

        FileVisitor visitor = new FileVisitor(es);

        Path root = Path.of(".");
        Files.walkFileTree(root, visitor);

        es.shutdown();
        try {
            if (!es.awaitTermination(1, TimeUnit.DAYS)) {
                es.shutdownNow();
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            es.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Done");
    }
}

package net.simon987.tika_test;

import org.apache.tika.config.ServiceLoader;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.concurrent.Callable;

public class ParseTask implements Callable<Void> {

    private final String filename;

    // Disable OCR
    private static final DefaultParser defaultParser = new DefaultParser(
            MediaTypeRegistry.getDefaultRegistry(),
                new ServiceLoader(),
                Collections.singletonList(TesseractOCRParser.class));

    private static final AutoDetectParser parser = new AutoDetectParser(defaultParser);

    public ParseTask(String filename) {
        this.filename = filename;
    }

    @Override
    public Void call() throws Exception {
        parseFile(filename);
        return null;
    }

    private static void parseFile(String filename) {

        try {
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();

            InputStream stream = new BufferedInputStream(new FileInputStream(filename));

            parser.parse(stream, handler, metadata);
            System.out.println(filename);

            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

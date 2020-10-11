package ru.ncedu.tdakkota.app;

import ru.ncedu.tdakkota.converter.BundleWriter;
import ru.ncedu.tdakkota.converter.JavaParser;
import ru.ncedu.tdakkota.converter.Line;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BundleConverterApp {
    private static void run(Path input, Path output) throws IOException {
        List<Line> lines = new JavaParser().parseFile(input);
        BundleWriter.writeToFile(output.toFile(), lines);
    }

    private static Path getOutputFilePath(Path p) {
        String name = p.getFileName().toString();
        int i = name.indexOf('.');
        if (i > 0) {
            return Paths.get(name.substring(0, i + 1) + ".properties");
        }

        return Paths.get(name + ".properties");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("usage: app.jar <INPUT_FILE> [OUTPUT_FILE]");
            return;
        }

        Path input = Paths.get(args[0]);
        Path output;

        if (args.length >= 2) {
            output = Paths.get(args[1]);
        } else {
            output = getOutputFilePath(input);
        }

        try {
            run(input, output);
        } catch (IOException e) {
            System.out.println("Conversion failed: " + e.getMessage());
        }
    }
}

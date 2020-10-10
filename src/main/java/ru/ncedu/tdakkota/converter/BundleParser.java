package ru.ncedu.tdakkota.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public interface BundleParser {
    public List<Line> parse(InputStream s) throws IOException;

    public default List<Line> parseFile(Path p) throws IOException {
        return parse(new FileInputStream(p.toFile()));
    }

    public default Map<Path, List<Line>> parseDirectory(Path p) throws IOException {
        if (!Files.isDirectory(p)) {
            return Collections.singletonMap(p, parseFile(p));
        }

        Map<Path, List<Line>> m = new HashMap<>();
        for (File f : Objects.requireNonNull(p.toFile().listFiles())) {
            m.put(f.toPath(), parseFile(f.toPath()));
        }
        return m;
    }
}


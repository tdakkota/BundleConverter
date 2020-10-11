package ru.ncedu.tdakkota.converter;

import java.io.*;
import java.util.List;

import static ru.ncedu.tdakkota.converter.ASCIIUtil.writeASCII;

public class BundleWriter implements Closeable, AutoCloseable {
    private OutputStreamWriter w;

    public BundleWriter(OutputStream s) {
        this.w = new OutputStreamWriter(s);
    }

    public BundleWriter(File f) throws FileNotFoundException {
        this.w = new OutputStreamWriter(new FileOutputStream(f));
    }

    public static void writeToFile(File f, List<Line> lines) throws IOException {
        new BundleWriter(f).write(lines);
    }

    public void write(List<Line> lines) throws IOException {
        for (Line line : lines) {
            String comment = line.getComment();
            if (comment != null) {
                w.append("#");
                // FIXME: Should we convert comment to ASCII too?
                w.append(comment);
                w.append(System.lineSeparator());
            }
            writeASCII(line.getKey(), w);
            w.append('=');
            writeASCII(line.getValue(), w);
            w.append(System.lineSeparator());
        }
        w.flush();
    }

    @Override
    public void close() throws IOException {
        w.close();
    }
}

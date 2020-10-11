package ru.ncedu.tdakkota.converter;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.ncedu.tdakkota.converter.ASCIIUtil.encodeASCII;

class BundleWriterTest {

    @Test
    void write() throws IOException {
        final ByteArrayOutputStream s = new ByteArrayOutputStream();
        BundleWriter w = new BundleWriter(s);
        List<Line> l = new LinkedList<>();
        l.add(new Line("key", "value", "comment"));
        l.add(new Line("key2", "value2"));
        l.add(new Line("ключ", "значение", "комментарий"));
        w.write(l);

        String[] expectedLines = new String[]{
                "#comment",
                "key=value",
                "key2=value2",
                "#комментарий",
                encodeASCII("ключ") + "=" + encodeASCII("значение"),
                "",
        };

        assertEquals(String.join(System.lineSeparator(), expectedLines), s.toString());
    }
}
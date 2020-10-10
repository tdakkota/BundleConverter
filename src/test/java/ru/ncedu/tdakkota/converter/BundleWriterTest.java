package ru.ncedu.tdakkota.converter;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.ncedu.tdakkota.converter.ASCIIUtil.encodeASCII;

class BundleWriterTest {

    @Test
    void write() throws IOException {
        final ByteArrayOutputStream s = new ByteArrayOutputStream();
        BundleWriter w = new BundleWriter(s);
        List<Line> l = new LinkedList<>();
        l.add(new Line("key", "value", "comment"));
        l.add(new Line("ключ", "значение", "комментарий"));
        w.write(l);

        String[] lines = s.toString().split("\\r?\\n");
        assertEquals("#comment", lines[0]);
        assertEquals("key=value", lines[1]);
        assertEquals("#комментарий", lines[2]);
        assertEquals(encodeASCII("ключ") + "=" + encodeASCII("значение"), lines[3]);
    }
}
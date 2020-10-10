package ru.ncedu.tdakkota.converter;

import java.io.IOException;

public class ASCIIUtil {
    public static String encodeASCII(String in) {
        final StringBuilder s = new StringBuilder(in.length());
        try {
            writeASCII(in, s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s.toString();
    }

    public static void writeASCII(String in, Appendable out) throws IOException {
        for (int i = 0; i < in.length(); i++) {
            final char ch = in.charAt(i);
            if (ch <= 127) out.append(ch);
            else {
                out.append("\\u");
                out.append(String.format("%04x", (int) ch));
            }
        }
    }
}

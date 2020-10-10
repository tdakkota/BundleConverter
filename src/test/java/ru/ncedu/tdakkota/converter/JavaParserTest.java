package ru.ncedu.tdakkota.converter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaParserTest {

    @Test
    void parse() throws IOException {
        JavaParser p = new JavaParser();
        List<Line> l = p.parse(getClass().getClassLoader().getResourceAsStream("test/Test.java"));
        String[][] lines = new String[][]{
                {"Error", "Error", null},
                {"File", "File", null},
                {"inFile", "in file", null},
                {"notFound", "not found", null},
                {"CantDelete", "Can't delete", null},
                {"CantCreate", "Can't create", "not used yet"}
        };


        for (Line line : l) {
            int index = l.indexOf(line);
            assertEquals(line.getKey(), lines[index][0]);
            assertEquals(line.getValue(), lines[index][1]);
            assertEquals(line.getComment(), lines[index][2]);
        }
    }
}
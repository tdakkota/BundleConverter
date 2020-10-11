package ru.ncedu.tdakkota.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaParserTest {
    private Map<String, String[][]> lines = new HashMap<>();

    @BeforeEach
    void setUp() {
        lines.put("Test.java", new String[][]{
                {"Error", "Error", null},
                {"File", "File", null},
                {"inFile", "in file", null},
                {"notFound", "not found", null},
                {"CantDelete", "Can't delete", null},
                {"CantCreate", "Can't create", "not used yet"}
        });

        lines.put("Test_ru.java", new String[][]{
                {"Error", "Ошибка", null},
                {"File", "Файл", null},
                {"inFile", "в файле", null},
                {"notFound", "не найден", null},
                {"CantDelete", "Невозможно удалить", null},
                {"CantCreate", "Невозможно создать", "not used yet"}
        });
    }

    @Test
    void parse() throws IOException, URISyntaxException {
        JavaParser p = new JavaParser();
        URI resourcePath = getClass().getClassLoader().getResource("test/").toURI();
        Map<Path, List<Line>> m = p.parseDirectory(Paths.get(resourcePath));

        for (Map.Entry<Path, List<Line>> e : m.entrySet()) {
            String fileName = e.getKey().getFileName().toString();
            String[][] lines = this.lines.get(fileName);
            List<Line> l = e.getValue();

            for (Line line : l) {
                int index = l.indexOf(line);
                assertEquals(line.getKey(), lines[index][0]);
                assertEquals(line.getValue(), lines[index][1]);
                assertEquals(line.getComment(), lines[index][2]);
            }
        }
    }
}
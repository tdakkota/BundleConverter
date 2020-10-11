package ru.ncedu.tdakkota.converter;

public class Line {
    private String key;
    private String value;
    private String comment;

    public Line(String key, String value) {
        this(key, value, null);
    }

    public Line(String key, String value, String comment) {
        this.key = key;
        this.value = value;
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }
}

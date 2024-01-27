package com.tritonkor.grouptester.persistence.repository.json.impl;

import java.nio.file.Path;

public enum JsonPathFactory {

    USERS("users.json"),
    TESTS("tests.json"),
    GROUPS("groups.json"),
    RESULTS("results.json"),
    REPORTS("reports.json"),
    DATA("data");;

    private final String fileName;
    private static final String DATA_DIRECTORY = "data";

    JsonPathFactory(String fileName) {
        this.fileName = fileName;
    }

    public Path getPath() {
        return Path.of(DATA_DIRECTORY.toString(), this.fileName);
    }

    public Path getDataPath() {
        return Path.of(DATA_DIRECTORY);
    }
}

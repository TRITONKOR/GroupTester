package com.tritonkor.grouptester.persistence.repository.json.impl;

import java.nio.file.Path;

public enum JsonPathFactory {

    USERS("users.json"),
    TESTS("tests.json"),
    GROUPS("groups.json"),
    RESULTS("results.json"),
    REPORTS("reports.json");

    private static final String DATA_DIRECTORY = "data";
    private final String fileName;

    JsonPathFactory(String fileName) {
        this.fileName = fileName;
    }

    public Path getPath() {
        return Path.of(DATA_DIRECTORY, this.fileName);
    }
}

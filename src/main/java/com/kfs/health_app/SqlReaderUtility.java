package com.kfs.health_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SqlReaderUtility {
    private static final Logger logger = LoggerFactory.getLogger(SqlReaderUtility.class);

    private static final String GET_ALL_WORKOUTS_FOR_USER = "sql/getAllWorkoutsForUser.sql";

    public static String getAllWorkoutsForUser() {
        return SqlReaderUtility.readSqlString(GET_ALL_WORKOUTS_FOR_USER);
    }

    private static String readSqlString(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            SqlReaderUtility.logger.error(e.getMessage());
            return "";
        }
    }
}

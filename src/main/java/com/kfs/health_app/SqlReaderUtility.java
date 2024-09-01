package com.kfs.health_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class SqlReaderUtility {
    private static final Logger logger = LoggerFactory.getLogger(SqlReaderUtility.class);

    private static final String GET_ALL_WORKOUTS_FOR_USER = "sql/getAllWorkoutsForUser.sql";

    public static String getAllWorkoutsForUser() {
        return SqlReaderUtility.readSqlString(GET_ALL_WORKOUTS_FOR_USER);
    }

    private static String readSqlString(String filePath) {
        try {
            Resource resource = new ClassPathResource(GET_ALL_WORKOUTS_FOR_USER);
            //Read File Content
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return (String)reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            SqlReaderUtility.logger.error(e.getMessage());
            return "";
        }
    }
}

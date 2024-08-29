package com.kfs.health_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SqlReaderUtility {
    private static final Logger logger = LoggerFactory.getLogger(SqlReaderUtility.class);

    private static final String GET_ALL_WORKOUTS_FOR_USER = "sql/getAllWorkoutsForUser.sql";

    private static final String sql = """
            SELECT *
  FROM public."tblWorkouts" AS w
  JOIN public."tblSetGroups" AS sg
      ON w."WkID" = sg."SetGroupWkID"
  JOIN public."tblSets" AS s
      ON sg."SetGroupID" = s."SetGroupID"
  JOIN public."tblExercises" AS ex
  	  ON s."SetExID" = ex."ExID"
WHERE w."WkUserID"::text = :userId;
            """;

    public static String getAllWorkoutsForUser() {
        //return SqlReaderUtility.readSqlString(GET_ALL_WORKOUTS_FOR_USER);
        return sql;
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

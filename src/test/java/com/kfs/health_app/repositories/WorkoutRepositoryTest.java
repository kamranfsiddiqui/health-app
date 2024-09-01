package com.kfs.health_app.repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
class WorkoutRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate template;

    @Test
    public void testGetWorkoutsByUserId_returnsList() {
        WorkoutRepository repo = new WorkoutRepository(template);

        Mockito.doAnswer(invocationOnMock -> {

            ResultSet resultSet = Mockito.mock(ResultSet.class);
            Mockito.when(resultSet.getString("workoutName")).thenReturn("1");
            Mockito.when(resultSet.getString("completionDate")).thenReturn("1");

            ResultSetExtractor<Map<String, String>> resultSetExtractor =
                    invocationOnMock.getArgument(2);
            return resultSetExtractor.extractData(resultSet);

        }).when(template).query(
                Mockito.anyString(),
                Mockito.any(MapSqlParameterSource.class),
                Mockito.any(ResultSetExtractor.class)
        );
    }
}
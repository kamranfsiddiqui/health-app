package com.kfs.health_app.repositories;

import com.kfs.health_app.SqlReaderUtility;
import com.kfs.health_app.dto.SetExerciseGroupDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
public class WorkoutRepository {
    private static final String GET_ALL_WORKOUTS_BY_USERID = SqlReaderUtility.getAllWorkoutsForUser();

    private final NamedParameterJdbcTemplate healthJdbcTemplate;

    public WorkoutRepository(NamedParameterJdbcTemplate healthJdbcTemplate) {
        this.healthJdbcTemplate = healthJdbcTemplate;
    }

    public List<SetExerciseGroupDto> getWorkoutsByUserId(String userId) throws WorkoutRepositoryException {
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        try {
            return this.healthJdbcTemplate.query(GET_ALL_WORKOUTS_BY_USERID, params, new SetRowMapper());
        } catch (DataAccessException e) {
            return List.of();
        } catch (Exception e) {
            throw new WorkoutRepositoryException(e);
        }
    }

    private static class SetRowMapper implements RowMapper<SetExerciseGroupDto> {
        /**
         * @param rs
         * @param rowNum
         * @return
         * @throws SQLException
         */
        @Override
        public SetExerciseGroupDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            SetExerciseGroupDto setExerciseGroup = new SetExerciseGroupDto(
                    rs.getInt("workoutId"),
                    rs.getString("workoutName"),
                    rs.getString("completionDate"),
                    rs.getString("userId"),
                    rs.getInt("setGroupId"),
                    rs.getString("exercise"),
                    rs.getInt("setCount"),
                    Arrays.asList((Short[]) rs.getArray("repetitions").getArray()),
                    Arrays.asList((Float[]) rs.getArray("weights").getArray()),
                    Arrays.asList((Short[]) rs.getArray("intensities").getArray()),
                    Arrays.asList((Short[]) rs.getArray("restPeriods").getArray()),
                    rs.getString("unit"),
                    rs.getString("unitShortName")
            );
            return setExerciseGroup;
        }
    }


}

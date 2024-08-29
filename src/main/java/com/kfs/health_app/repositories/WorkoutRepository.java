package com.kfs.health_app.repositories;

import com.kfs.health_app.SqlReaderUtility;
import com.kfs.health_app.generated.model.Set;
import com.kfs.health_app.generated.model.Workout;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class WorkoutRepository {
    private static final String GET_ALL_WORKOUTS_BY_USERID = SqlReaderUtility.getAllWorkoutsForUser();

    private final NamedParameterJdbcTemplate healthJdbcTemplate;

    public WorkoutRepository(NamedParameterJdbcTemplate healthJdbcTemplate) {
        this.healthJdbcTemplate = healthJdbcTemplate;
    }

    public List<Workout> getAllWorkoutsByUserId(String userId) {
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        List<Workout> workouts = null;
        try {
            workouts = this.healthJdbcTemplate.query(GET_ALL_WORKOUTS_BY_USERID, params, new SetRowMapper());
        } catch (BadSqlGrammarException e) {
            return List.of();
        }
        return workouts;
    }

    private class SetRowMapper implements RowMapper<Set> {

        /**
         * @param rs
         * @param rowNum
         * @return
         * @throws SQLException
         */
        @Override
        public Set mapRow(ResultSet rs, int rowNum) throws SQLException {
            Workout workout = new Workout();
            workout.setName(rs.getString("WkName"));
            workout.setCompletionDate(LocalDate.parse(rs.getString("WkDateCompleted")));
            return workout;
        }
    }
}

package com.kfs.health_app.repositories;

import com.kfs.health_app.SqlReaderUtility;
import com.kfs.health_app.dto.SetExerciseGroupDto;
import com.kfs.health_app.generated.model.SetExerciseGroup;
import com.kfs.health_app.generated.model.Workout;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class WorkoutRepository {
    private static final String GET_ALL_WORKOUTS_BY_USERID = SqlReaderUtility.getAllWorkoutsForUser();

    private final NamedParameterJdbcTemplate healthJdbcTemplate;

    public WorkoutRepository(NamedParameterJdbcTemplate healthJdbcTemplate) {
        this.healthJdbcTemplate = healthJdbcTemplate;
    }

    public List<Workout> getAllWorkoutsByUserId(String userId) {
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        List<Workout> workouts = List.of();
        try {
            List<SetExerciseGroupDto> rawSetData = this.healthJdbcTemplate.query(GET_ALL_WORKOUTS_BY_USERID, params, new SetRowMapper());
            workouts = this.transformRawSetData(rawSetData);
        } catch (DataAccessException e) {
            return List.of();
        }
        return workouts;
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
                    rs.getInt("setGroupId"),
                    rs.getInt("exerciseId"),
                    rs.getString("exercise"),
                    rs.getInt("setCount"),
                    Stream.of(rs.getString("repetitions").split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()),
                    Stream.of(rs.getString("weights").split(","))
                            .map(Float::parseFloat)
                            .collect(Collectors.toList()),
                    Stream.of(rs.getString("intensities").split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()),
                    Stream.of(rs.getString("restPeriods").split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList())
            );
            return setExerciseGroup;
        }
    }

    private List<Workout> transformRawSetData(List<SetExerciseGroupDto> rawSetData) {
        Map<Integer, List<SetExerciseGroup>> workoutMap = new LinkedHashMap<>();
        for(SetExerciseGroupDto setGroupDto : rawSetData) {
            SetExerciseGroup newGroup = new SetExerciseGroup()
                    .setGroupId(setGroupDto.setGroupId())
                    .totalSets(setGroupDto.setCount())
                    .repetitions(setGroupDto.repetitions())
                    .weights(setGroupDto.weights());
            if(!workoutMap.containsKey(setGroupDto.workoutId())) {
                workoutMap.put(setGroupDto.workoutId(), new ArrayList<>());
            }
            workoutMap.get(setGroupDto.workoutId()).add(newGroup);
        }
        List<Workout> workouts = new ArrayList<>();
        for(Map.Entry<Integer, List<SetExerciseGroup>> entry : workoutMap.entrySet()) {
            workouts.add(new Workout()
                    .workoutId(entry.getKey())
                    .setGroups(entry.getValue()));
        }
        return workouts;
    }
}

package com.kfs.health_app.services;

import com.kfs.health_app.dto.SetExerciseGroupDto;
import com.kfs.health_app.generated.model.SetExerciseGroup;
import com.kfs.health_app.generated.model.Workout;
import com.kfs.health_app.repositories.WorkoutRepository;
import com.kfs.health_app.repositories.WorkoutRepositoryException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<Workout> getWorkoutsByUserId(String userId) {
        try {
             List<SetExerciseGroupDto> rawSetData = this.workoutRepository.getWorkoutsByUserId(userId);
             return transformRawSetData(rawSetData);
        } catch (WorkoutRepositoryException e) {
            return List.of();
        }
    }

    /**
     * Transforms raw data of sets from the Database into workout objects that are returned by the API
     *
     * @param rawSetData - rows of
     * @return a list of workouts
     */
    private List<Workout> transformRawSetData(List<SetExerciseGroupDto> rawSetData) {
        Map<Integer, Workout> workoutMap = new LinkedHashMap<>();
        for(SetExerciseGroupDto setGroupDto : rawSetData) {
            int workoutId = setGroupDto.workoutId();

            SetExerciseGroup newGroup = new SetExerciseGroup()
                    .setGroupId(setGroupDto.setGroupId())
                    .exercise(setGroupDto.exercise())
                    .totalSets(setGroupDto.setCount())
                    .repetitions(shortListToIntList(setGroupDto.repetitions()))
                    .weights(setGroupDto.weights())
                    .unit(setGroupDto.unit())
                    .unitAbbreviation(setGroupDto.unitAbbreviation())
                    .intensities(shortListToIntList(setGroupDto.intensities()));

            if(!workoutMap.containsKey(workoutId)) {
                OffsetDateTime completionDate = OffsetDateTime.parse(
                        setGroupDto.completionDate(),
                        DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSSSSSxxx"));

                Workout workout = new Workout()
                        .workoutId(workoutId)
                        .userId(UUID.fromString(setGroupDto.userId()))
                        .name(setGroupDto.name())
                        .completionDate(completionDate);
                workoutMap.put(workoutId, workout);
            }

            workoutMap.get(workoutId).addSetExerciseGroupsItem(newGroup);
        }
        return new ArrayList<>(workoutMap.values());
    }

    private List<Integer> shortListToIntList(List<Short> shorts) {
       return shorts.get(0) != null
              ? shorts.stream().map(Integer::valueOf).collect(Collectors.toList())
              : List.of();
    }
}

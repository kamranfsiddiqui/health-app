package com.kfs.health_app.dto;

import java.util.List;

public record SetExerciseGroupDto(
    int workoutId,
    int setGroupId,
    int exerciseId,
    String exercise,
    int setCount,
    List<Integer> repetitions,
    List<Float> weights,
    List<Integer> intensities,
    List<Integer> restPeriods
) { }

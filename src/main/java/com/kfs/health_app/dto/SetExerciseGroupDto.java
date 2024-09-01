package com.kfs.health_app.dto;

import java.util.List;

public record SetExerciseGroupDto(
    int workoutId,
    String name,
    String completionDate,
    String userId,
    int setGroupId,
    String exercise,
    int setCount,
    List<Short> repetitions,
    List<Float> weights,
    List<Short> intensities,
    List<Short> restPeriods,
    String unit,
    String unitAbbreviation
) { }

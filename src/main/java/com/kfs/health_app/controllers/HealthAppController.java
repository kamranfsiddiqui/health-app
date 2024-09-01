package com.kfs.health_app.controllers;

import com.kfs.health_app.generated.api.WorkoutApi;
import com.kfs.health_app.generated.model.Workout;
import com.kfs.health_app.services.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class HealthAppController implements WorkoutApi {

    public WorkoutService workoutService;

    public HealthAppController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Health App API is up and running", HttpStatus.OK);
    }

    /**
     * @param userId  the id of the current user (required)
     * @param workout (optional)
     * @return
     */
    @Override
    public ResponseEntity<Void> createWorkout(UUID userId, Workout workout) {
        return WorkoutApi.super.createWorkout(userId, workout);
    }

    /**
     * @param userId the id of the current user (required)
     * @return
     */
    @Override
    public ResponseEntity<List<Workout>> getWorkoutByUserId(UUID userId) {
        try {
            List<Workout> workouts = this.workoutService.getWorkoutsByUserId(userId.toString());
            return ResponseEntity.ok().header("custom-header", "foo").body(workouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

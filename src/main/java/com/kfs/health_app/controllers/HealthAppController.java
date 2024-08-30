package com.kfs.health_app.controllers;

import com.kfs.health_app.generated.api.WorkoutApi;
import com.kfs.health_app.generated.model.Workout;
import com.kfs.health_app.repositories.WorkoutRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class HealthAppController implements WorkoutApi {

    public WorkoutRepository workoutRepository;

    public HealthAppController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("WORKING--------fdasfasdfadsfasdfa", HttpStatus.OK);
    }
    /**
     * @param userId the id of the current user (required)
     * @return
     */
    @Override
    public ResponseEntity<List<Workout>> getWorkoutByUserId(UUID userId) {
        try {
            List<Workout> workouts = this.workoutRepository.getAllWorkoutsByUserId(userId.toString());
            return ResponseEntity.ok().header("custom-header", "foo").body(workouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.kfs.health_app.controllers;

import com.kfs.health_app.generated.api.UserApiController;
import com.kfs.health_app.generated.api.UserApiDelegate;
import com.kfs.health_app.generated.api.WorkoutApiDelegate;
import com.kfs.health_app.generated.model.SetGroup;
import com.kfs.health_app.generated.model.User;
import com.kfs.health_app.generated.model.Workout;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.kfs.health_app.generated.api.WorkoutApiController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class HealthAppController implements WorkoutApiDelegate, UserApiDelegate {

    /**
     * @param user Created user object (optional)
     * @return
     */
    @Operation(
            operationId = "createUser",
            summary = "Create user",
            description = "This can only be done by the logged in user.",
            tags = { "user" },
            responses = {
                    @ApiResponse(responseCode = "default", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/user",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    @Override
    public ResponseEntity<User> createUser(User user) {
        return UserApiDelegate.super.createUser(user);
    }

    /**
     * @param user (optional)
     * @return
     */
    @Operation(
            operationId = "createUsersWithListInput",
            summary = "Creates list of users with given input array",
            description = "Creates list of users with given input array",
            tags = { "user" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))
                    }),
                    @ApiResponse(responseCode = "default", description = "successful operation")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/user/createWithList",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json" }
    )
    @Override
    public ResponseEntity<User> createUsersWithListInput(List<@Valid User> user) {
        return UserApiDelegate.super.createUsersWithListInput(user);
    }

    /**
     * @param userId The name that needs to be fetched. Use user1 for testing.  (required)
     * @return
     */
    @Operation(
            operationId = "deleteUser",
            summary = "Delete user",
            description = "This can only be done by the logged in user.",
            tags = { "user" },
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/user/{userId}"
    )
    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        return UserApiDelegate.super.deleteUser(userId);
    }

    /**
     * @param userId The name that needs to be fetched. Use user1 for testing.  (required)
     * @return
     */
    @Operation(
            operationId = "getUserById",
            summary = "Get user by user name",
            description = "",
            tags = { "user" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/user/{userId}",
            produces = { "application/json", "application/xml" }
    )
    @Override
    public ResponseEntity<User> getUserById(String userId) {
        return UserApiDelegate.super.getUserById(userId);
    }

    /**
     * @return
     */
    @Operation(
            operationId = "loginUser",
            summary = "Logs user into the system",
            description = "",
            tags = { "user" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = String.class)),
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid username/password supplied")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/user/login",
            produces = { "application/xml", "application/json" }
    )
    @Override
    public ResponseEntity<String> loginUser() {
        return UserApiDelegate.super.loginUser();
    }

    /**
     * @return
     */
    @Operation(
            operationId = "logoutUser",
            summary = "Logs out current logged in user session",
            description = "",
            tags = { "user" },
            responses = {
                    @ApiResponse(responseCode = "default", description = "successful operation")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/user/logout"
    )
    @Override
    public ResponseEntity<Void> logoutUser() {
        return UserApiDelegate.super.logoutUser();
    }

    /**
     * @param userId The name that needs to be fetched. Use user1 for testing.  (required)
     * @param user   Update an existent user in the store (optional)
     * @return
     */
    @Operation(
            operationId = "updateUser",
            summary = "Update user",
            description = "This can only be done by the logged in user.",
            tags = { "user" },
            responses = {
                    @ApiResponse(responseCode = "default", description = "successful operation")
            }
    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/user/{userId}",
            consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }
    )
    @Override
    public ResponseEntity<Void> updateUser(String userId, User user) {
        return UserApiDelegate.super.updateUser(userId, user);
    }

    /**
     * @return
     */
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return WorkoutApiDelegate.super.getRequest();
    }

    /**
     * @param id       The id of the set-group we want add more sets to (required)
     * @param setGroup (optional)
     * @return
     */
    @Operation(
            operationId = "createSetGroup",
            summary = "Create a group of sets",
            tags = { "workout" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "success")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/workout/{id}/setGroup",
            consumes = { "application/json" }
    )
    @Override
    public ResponseEntity<Void> createSetGroup(Long id, SetGroup setGroup) {
        return WorkoutApiDelegate.super.createSetGroup(id, setGroup);
    }

    /**
     * @param workout (optional)
     * @return
     */
    @Operation(
            operationId = "createWorkout",
            summary = "create a new workout",
            tags = { "workout" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/workout",
            consumes = { "application/json" }
    )
    @Override
    public ResponseEntity<Void> createWorkout(Workout workout) {
        return WorkoutApiDelegate.super.createWorkout(workout);
    }

    /**
     * @param id       The id of the set-group we want add more sets to (required)
     * @param setGroup (optional)
     * @return
     */
    @Operation(
            operationId = "getWorkoutByUserId",
            summary = "get info about workout associated with the given id",
            tags = { "workout" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/workout/user/{id}",
            consumes = { "application/json" }
    )
    @Override
    public ResponseEntity<Void> getWorkoutByUserId(Long id, SetGroup setGroup) {
        return WorkoutApiDelegate.super.getWorkoutByUserId(id, setGroup);
    }

    /**
     * @param id       The id of the set-group we want add more sets to (required)
     * @param groupId  The id of the set-group we want add more sets to (required)
     * @param setGroup (optional)
     * @return
     */
    @Operation(
            operationId = "setGroupAddSets",
            summary = "add a new group of sets to the set group with the given id",
            tags = { "workout" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/workout/{id}/setGroup/{groupId}",
            consumes = { "application/json" }
    )
    @Override
    public ResponseEntity<Void> setGroupAddSets(Long id, Integer groupId, SetGroup setGroup) {
        return WorkoutApiDelegate.super.setGroupAddSets(id, groupId, setGroup);
    }
}

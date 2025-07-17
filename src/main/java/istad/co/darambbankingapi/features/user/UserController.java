package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.base.BasedMessage;
import istad.co.darambbankingapi.features.user.dto.ChangePasswordUser;
import istad.co.darambbankingapi.features.user.dto.UpdateProfileUser;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/details")
    public ResponseEntity<?> getDetailsAllUsers() {
        return new ResponseEntity<>(
                Map.of(
                        "users", userService.getAllUsersDetails()
                ), HttpStatus.OK
        );
    }
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(Map.of(
                "users", userService.getAllUsers()
        ), HttpStatus.OK);
    }

    @GetMapping("/snippets")
    public ResponseEntity<?> getSnippetsAllUsers() {
        return new ResponseEntity<>(Map.of(
                "users",userService.getAllUserSnippets()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createNew(userCreateRequest);
        return new ResponseEntity<>(Map.of(
                "message", "User created successfully"
        ), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/change-password")
    public ResponseEntity<?> changePasswordUser(@PathVariable String uuid, @Valid @RequestBody ChangePasswordUser changePasswordUser){
        userService.changePasswordUser(uuid, changePasswordUser);
        return new ResponseEntity<>(
                Map.of(
                        "message", "User changed password successfully"
                ),HttpStatus.OK
        );
    }

    @PutMapping("/{uuid}/profile")
    public ResponseEntity<?> updateProfileUser(@PathVariable String uuid, @Valid @RequestBody UpdateProfileUser updateProfileUser){
        userService.updateProfileUser(uuid, updateProfileUser);
        return new ResponseEntity<>(
                Map.of(
                        "message", "User updated profile successfully"
                ), HttpStatus.OK
        );
    }

    @PutMapping("/{uuid}/block")
    public BasedMessage blockByUuid(@PathVariable String uuid) {
        return userService.blockByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuid) {
        userService.deleteByUuid(uuid);
        return new ResponseEntity<>(
                Map.of(
                        "message", "User deleted successfully"
                ),HttpStatus.OK
        );
    }

    @PutMapping("/{uuid}/disable")
    public BasedMessage disableUser(@PathVariable String uuid) {
        return userService.disableByUuid(uuid);
    }

    @PutMapping("/{uuid}/enable")
    public BasedMessage enableUser(@PathVariable String uuid) {
        return userService.enableByUuid(uuid);
    }
}

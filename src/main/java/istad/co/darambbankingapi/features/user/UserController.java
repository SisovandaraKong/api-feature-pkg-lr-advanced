package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.base.BasedMessage;
import istad.co.darambbankingapi.features.user.dto.ChangePasswordUser;
import istad.co.darambbankingapi.features.user.dto.UpdateProfileUser;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;
import istad.co.darambbankingapi.features.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false,defaultValue = "2")int size) {
        Page<UserResponse> userPage = userService.getAllUsers(page, size);
        return new ResponseEntity<>(Map.of(
                "users", userPage.getContent(),
                "page", userPage.getNumber(),
                "size", userPage.getSize(),
                "totalPages", userPage.getTotalPages(),
                "totalElements", userPage.getTotalElements()
        ), HttpStatus.OK);
    }

    @GetMapping("/snippets")
    public ResponseEntity<?> getSnippetsAllUsers() {
        return new ResponseEntity<>(Map.of(
                "users",userService.getAllUserSnippets()
        ), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(
                userService.getUserByUuid(uuid), HttpStatus.OK);
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

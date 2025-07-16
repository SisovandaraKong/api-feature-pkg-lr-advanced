package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.features.user.dto.ChangePasswordUser;
import istad.co.darambbankingapi.features.user.dto.UpdateProfileUser;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

}

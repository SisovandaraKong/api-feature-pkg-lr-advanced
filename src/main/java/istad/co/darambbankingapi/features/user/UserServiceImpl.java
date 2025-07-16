package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.domain.Role;
import istad.co.darambbankingapi.domain.User;
import istad.co.darambbankingapi.features.user.dto.ChangePasswordUser;
import istad.co.darambbankingapi.features.user.dto.UpdateProfileUser;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;
import istad.co.darambbankingapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    @Override
    public void createNew(UserCreateRequest userCreateRequest) {
    if (userRepository.existsByNationalCardId(userCreateRequest.nationalCardId())){
        throw new ResponseStatusException(HttpStatus.CONFLICT,"National Card already exists");
    }
    if (userRepository.existsByPhoneNumber(userCreateRequest.phoneNumber())){
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Phone number already exists");
    }
    if (userRepository.existsByStudentCardId(userCreateRequest.studentCardId())){
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Student Card already exists");
    }
    if (!userCreateRequest.password().equals(userCreateRequest.confirmedPassword())){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Confirmed Password does not match");
    }

        User user = userMapper.fromUserCreateRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setProfileImage("avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        //Assign default user role
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role USER not found"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void changePasswordUser(String uuid, ChangePasswordUser changePasswordUser) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User's uuid not found"));
        if (!changePasswordUser.oldPassword().equals(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Old Password does not match");
        }
        if (!changePasswordUser.confirmedPassword().equals(changePasswordUser.newPassword())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Confirmed password does not match");
        }
        user.setPassword(changePasswordUser.newPassword());
        userRepository.save(user);
    }

    @Override
    public void updateProfileUser(String uuid, UpdateProfileUser updateProfileUser) {
    User user = userRepository.findByUuid(uuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User's uuid not found"));
    userMapper.updateUser(updateProfileUser, user);
    userRepository.save(user);
    }
}

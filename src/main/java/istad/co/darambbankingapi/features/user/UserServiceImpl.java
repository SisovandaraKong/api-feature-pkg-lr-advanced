package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.base.BasedMessage;
import istad.co.darambbankingapi.domain.Role;
import istad.co.darambbankingapi.domain.User;
import istad.co.darambbankingapi.features.user.dto.*;
import istad.co.darambbankingapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDetailsResponse> getAllUsersDetails() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(userMapper::toUserDetailsResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public List<UserSnippetsResponse> getAllUserSnippets() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserSnippetResponse)
                .toList();
    }


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

        //Assign default user role by teacher
//        List<Role> roles = new ArrayList<>();
//        Role userRole = roleRepository.findByName("USER")
//                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role USER not found"));
//        //Create dynamic roles from clients
//        userCreateRequest.roles()
//                        .forEach(role -> {
//                            Role newRole = roleRepository.findByName(role.name())
//                                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role "+ userCreateRequest.roles()+" NOT found"));
//                            roles.add(newRole);
//                        });
//        roles.add(userRole);
//        user.setRoles(roles);
//        userRepository.save(user);

        // By me
        // Add default USER role
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Role USER not found"));

        //Assign default user role
        List<Role> roles = userCreateRequest.roles().stream()
                .map(role -> roleRepository.findByName(role.name())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Role " + role.name() + " not found")))
                .collect(Collectors.toList());


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
    log.info("User's name before update: {}", user.getName());
    log.info("User's dob before update: {}", user.getDob().toString());
    userMapper.updateUser(updateProfileUser, user);
    log.info("User's name after update: {}", user.getName());
    log.info("User's dob after update: {}", user.getDob().toString());
    userRepository.save(user);
    }

    @Transactional
    @Override
    public BasedMessage blockByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"User's uuid not found");
        }
        userRepository.blockByUuid(uuid);
        return new BasedMessage("User has been blocked");
    }

    @Transactional
    @Override
    public BasedMessage disableByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User's uuid not found");
        }
        userRepository.disableByUuid(uuid);
        return new BasedMessage("User has been disabled");
    }

    @Transactional
    @Override
    public BasedMessage enableByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User's uuid not found");
        }
        userRepository.enableByUuid(uuid);
        return new BasedMessage("User has been enabled");
    }

    @Transactional
    @Override
    public void deleteByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User's uuid not found");
        }
        userRepository.deleteByUuid(uuid);
    }
}

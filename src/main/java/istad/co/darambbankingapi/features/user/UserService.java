package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.base.BasedMessage;
import istad.co.darambbankingapi.features.user.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<UserDetailsResponse> getAllUsersDetails();
    Page<UserResponse> getAllUsers(int page, int size);
    List<UserSnippetsResponse> getAllUserSnippets();
    UserResponse getUserByUuid(String uuid);

    void createNew(UserCreateRequest userCreateRequest);

    void changePasswordUser(String uuid, ChangePasswordUser changePasswordUser);
    void updateProfileUser(String uuid, UpdateProfileUser updateProfileUser);

    BasedMessage blockByUuid(String uuid);
    BasedMessage disableByUuid(String uuid);
    BasedMessage enableByUuid(String uuid);
    void deleteByUuid(String uuid);
}

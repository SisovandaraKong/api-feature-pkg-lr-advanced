package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.features.user.dto.ChangePasswordUser;
import istad.co.darambbankingapi.features.user.dto.UpdateProfileUser;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;
import istad.co.darambbankingapi.features.user.dto.UserDetailsResponse;

import java.util.List;

public interface UserService {
    List<UserDetailsResponse> getAllUsersDetails();
    void createNew(UserCreateRequest userCreateRequest);
    void changePasswordUser(String uuid, ChangePasswordUser changePasswordUser);
    void updateProfileUser(String uuid, UpdateProfileUser updateProfileUser);
}

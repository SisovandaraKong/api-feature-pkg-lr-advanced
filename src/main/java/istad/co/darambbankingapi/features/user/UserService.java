package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.features.user.dto.ChangePasswordUser;
import istad.co.darambbankingapi.features.user.dto.UpdateProfileUser;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;

import java.util.List;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);
    void changePasswordUser(String uuid, ChangePasswordUser changePasswordUser);
    void updateProfileUser(String uuid, UpdateProfileUser updateProfileUser);
}

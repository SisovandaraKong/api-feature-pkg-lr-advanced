package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);
}

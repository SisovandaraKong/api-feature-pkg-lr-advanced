package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;

import java.util.List;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);
}

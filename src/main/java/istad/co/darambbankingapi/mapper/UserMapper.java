package istad.co.darambbankingapi.mapper;

import istad.co.darambbankingapi.domain.User;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;
import istad.co.darambbankingapi.features.user.dto.UserDetialsRespone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User requestToUser(UserCreateRequest userCreateRequest);
    UserDetialsRespone userToUserDetialsRespone(User user);
}

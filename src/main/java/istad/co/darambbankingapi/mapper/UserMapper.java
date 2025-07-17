package istad.co.darambbankingapi.mapper;

import istad.co.darambbankingapi.domain.Role;
import istad.co.darambbankingapi.domain.User;
import istad.co.darambbankingapi.features.user.dto.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // SourceType = UserCreateRequest (Parameter)
    // TargetType = User (ReturnType)
    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    UserDetailsResponse toUserDetailsResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UpdateProfileUser updateProfileUser, @MappingTarget User user);

    UserResponse toUserResponse(User user);

    UserSnippetsResponse toUserSnippetResponse(User user);
}

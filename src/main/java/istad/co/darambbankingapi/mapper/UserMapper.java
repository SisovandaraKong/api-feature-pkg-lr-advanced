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

    // Map User → UserDetailsResponse with custom role name list
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToNames")
    UserDetailsResponse toUserDetailsResponse(User user);
    // Custom method for mapping List<Role> → List<String>
    @Named("mapRolesToNames")
    default List<String> mapRolesToNames(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getName)
                .toList();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UpdateProfileUser updateProfileUser, @MappingTarget User user);

    UserResponse toUserResponse(User user);

    UserSnippetsResponse toUserSnippetResponse(User user);
}

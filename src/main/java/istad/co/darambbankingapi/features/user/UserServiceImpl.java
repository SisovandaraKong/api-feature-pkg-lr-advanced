package istad.co.darambbankingapi.features.user;

import istad.co.darambbankingapi.domain.User;
import istad.co.darambbankingapi.features.user.dto.UserCreateRequest;
import istad.co.darambbankingapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public void createNew(UserCreateRequest userCreateRequest) {

        User user = userMapper.fromUserCreateRequest(userCreateRequest);
        userRepository.save(user);

    }
}

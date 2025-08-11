package istad.co.darambbankingapi.features.auth;

import istad.co.darambbankingapi.features.auth.dto.AuthResponse;
import istad.co.darambbankingapi.features.auth.dto.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);
}

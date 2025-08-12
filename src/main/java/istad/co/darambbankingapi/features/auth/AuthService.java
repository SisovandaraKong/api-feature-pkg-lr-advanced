package istad.co.darambbankingapi.features.auth;

import istad.co.darambbankingapi.features.auth.dto.AuthResponse;
import istad.co.darambbankingapi.features.auth.dto.LoginRequest;
import istad.co.darambbankingapi.features.auth.dto.RefreshTokenRequest;

public interface AuthService {

    AuthResponse refresh(RefreshTokenRequest refreshTokenRequest);

    AuthResponse login(LoginRequest loginRequest);
}

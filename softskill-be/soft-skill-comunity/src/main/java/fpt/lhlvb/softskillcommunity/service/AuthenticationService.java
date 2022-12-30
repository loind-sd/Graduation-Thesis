package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.model.request.LoginRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.ForgotPasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RegisterUserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.LoginResponse;
import fpt.lhlvb.softskillcommunity.securities.oauth2.OAuth2UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    ApiResponse registerUser(RegisterUserRequest request);

    Long registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo);

    Long updateOauth2User(Users user, String provider, OAuth2UserInfo oAuth2UserInfo);

}

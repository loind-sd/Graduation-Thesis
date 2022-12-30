package fpt.lhlvb.softskillcommunity.securities.oauth2;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.securities.UserDetailsImpl;
import fpt.lhlvb.softskillcommunity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());
        Optional<Users> usersOptional = usersRepository.findByMail(oAuth2UserInfo.getEmail());
        Long id;
        if (usersOptional.isEmpty()) {
            id = authenticationService.registerOauth2User(provider, oAuth2UserInfo);
        } else {
            if (usersOptional.get().isDeleteFlag()) {
                return null;
            }
            id = authenticationService.updateOauth2User(usersOptional.get(), provider, oAuth2UserInfo);
        }
        Optional<Users> user = usersRepository.findByIdAndDeleteFlag(id, CommonConstant.DELETE_FLAG_FALSE);
        return UserDetailsImpl.create(user.get(), oAuth2User.getAttributes());
    }
}

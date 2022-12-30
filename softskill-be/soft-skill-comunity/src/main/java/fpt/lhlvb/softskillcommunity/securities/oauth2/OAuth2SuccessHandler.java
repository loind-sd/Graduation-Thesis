package fpt.lhlvb.softskillcommunity.securities.oauth2;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Account;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.repository.AccountRepository;
import fpt.lhlvb.softskillcommunity.repository.RolesRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.securities.UserDetailsImpl;
import fpt.lhlvb.softskillcommunity.securities.jwt.JwtTokenUtils;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private  JwtTokenUtils jwtTokenUtils;

    @Value("${hostname}")
    private String hostname;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        Optional<Users> usersOptional = usersRepository.findByMailAndDeleteFlag(email, CommonConstant.DELETE_FLAG_FALSE);
        Users user = new Users();

        if (usersOptional.isEmpty()) {
            Optional<Account> accountOptional = accountRepository.findByUsernameAndDeleteFlag(email, CommonConstant.DELETE_FLAG_FALSE);
            if (accountOptional.isPresent()) {
                user = accountOptional.get().getUser();
            } else {
                throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
            }
        } else {
            user = usersOptional.get();
        }
        List<String> roles = UserDetailsImpl.build(user).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtTokenUtils.generateJwtToken(email, "USER");
        String uri = UriComponentsBuilder.fromUriString("http://" + hostname + "/oauth2/redirect")
                .queryParam("token", token)
                .queryParam("role",roles)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
}

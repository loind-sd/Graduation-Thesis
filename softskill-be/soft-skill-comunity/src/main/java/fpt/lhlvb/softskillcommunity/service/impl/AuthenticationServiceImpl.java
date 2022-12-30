package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Account;
import fpt.lhlvb.softskillcommunity.entity.InviteUser;
import fpt.lhlvb.softskillcommunity.entity.Roles;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.AuthProvider;
import fpt.lhlvb.softskillcommunity.model.ERole;
import fpt.lhlvb.softskillcommunity.model.request.LoginRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RegisterUserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.LoginResponse;
import fpt.lhlvb.softskillcommunity.repository.AccountRepository;
import fpt.lhlvb.softskillcommunity.repository.InviteUserRepository;
import fpt.lhlvb.softskillcommunity.repository.RolesRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.securities.UserDetailsImpl;
import fpt.lhlvb.softskillcommunity.securities.jwt.JwtTokenUtils;
import fpt.lhlvb.softskillcommunity.securities.oauth2.OAuth2UserInfo;
import fpt.lhlvb.softskillcommunity.service.AuthenticationService;
import fpt.lhlvb.softskillcommunity.service.InviteUserService;
import fpt.lhlvb.softskillcommunity.service.MailService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private InviteUserService inviteUserService;

    @Autowired
    private InviteUserRepository inviteUserRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MailService mailService;

    @Override
    public LoginResponse login(LoginRequest request) {
        String condition = request.getCondition().trim();
        Optional<Users> usersOptional = usersRepository.findByMailAndDeleteFlag(condition, CommonConstant.DELETE_FLAG_FALSE);
        Users user;

        if (usersOptional.isEmpty()) {
            Optional<Account> accountOptional = accountRepository.findByUsernameAndDeleteFlag(condition, CommonConstant.DELETE_FLAG_FALSE);
            if (accountOptional.isPresent()) {
                user = accountOptional.get().getUser();
            } else {
                throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
            }
        } else {
            user = usersOptional.get();
        }
        String password = CommonUtils.decrypt(user.getAccount().getPassword(), CommonConstant.SECRET);
        String jwt = jwtTokenUtils.generateJwtToken(user.getAccount().getUsername(), user.getAccount().getRoles().getRoleName());
        List<String> roles = UserDetailsImpl.build(user).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        if (password != null && !password.equals(request.getPassword())) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        return new LoginResponse(user.getMail(), jwt, jwtTokenUtils.getExtAt(jwt).getTime(), roles);
    }

    @Override
    public ApiResponse registerUser(RegisterUserRequest request) {
        String nickName = "@" + request.getUsername() + CommonUtils.randomString(3);

        Users user = new Users();
        user.setName(request.getName());
        user.setNickname(nickName);
        user.setMail(request.getMail());
        user.setGender(request.getGender());
        user.setBirthdate(request.getBirthdate());
        user.setCommonRegister(commonService.idUserAccountLogin());
        usersRepository.save(user);

        Account account = new Account();
        Optional<Roles> rolesOptional = rolesRepository.findById(request.getRoleId());
        account.setRoles(rolesOptional.get());
        account.setUsername(request.getUsername());
        account.setUser(user);
        account.setPassword(CommonUtils.encrypt(request.getPassword(), CommonConstant.SECRET));
        account.setCommonRegister(commonService.idUserAccountLogin());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS,
                accountRepository.save(account).getId());

    }

    @Override
    public Long registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        Users user = new Users();
        String accountName = oAuth2UserInfo.getEmail().split("@")[0];
        String nickName = "@" + accountName;
        String name = StringUtils.trimToEmpty(oAuth2UserInfo.getFirstName()) + " "
                + StringUtils.trimToEmpty(oAuth2UserInfo.getLastName());
        user.setName(name);
        user.setNickname(nickName);
        user.setMail(oAuth2UserInfo.getEmail());
        user.setIsOnline(false);
        user.setPicture(oAuth2UserInfo.getPicture());
        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
        user.setCommonRegister(0L);
        usersRepository.save(user);
        user.setCommonRegister(user.getId());
        usersRepository.save(user);

        Optional<InviteUser> inviteUser = inviteUserRepository.findByMailOver7Days(oAuth2UserInfo.getEmail());
        Account account = new Account();
        Optional<Roles> rolesOptional;
        if (inviteUser.isPresent()) {
            rolesOptional = rolesRepository.findByRoleNameAndDeleteFlag(inviteUser.get().getRole(), CommonConstant.DELETE_FLAG_FALSE);
            inviteUserService.deleteInvite(inviteUser.get());
        } else {
            rolesOptional = rolesRepository.findByRoleNameAndDeleteFlag(ERole.USER.toString(), CommonConstant.DELETE_FLAG_FALSE);
        }
        account.setRoles(rolesOptional.get());
        account.setUsername(accountName);
        account.setUser(user);

        String newPassword = CommonUtils.randomString(8);
        Map<String, Object> resetPassword = new HashMap<>();
        resetPassword.put("to", oAuth2UserInfo.getEmail());
        resetPassword.put("subject", "Mật khẩu cho Website SoftSkill");
        resetPassword.put("password", newPassword);

        mailService.sendInitPasswordMail(resetPassword);
        account.setPassword(CommonUtils.encrypt(newPassword.trim(), CommonConstant.SECRET));
        account.setCommonRegister(user.getId());
        accountRepository.save(account);
        return user.getId();
    }

    @Override
    public Long updateOauth2User(Users user, String provider, OAuth2UserInfo oAuth2UserInfo) {
        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
        user.setPicture(oAuth2UserInfo.getPicture());
        user.setCommonUpdate(user.getId());
        return usersRepository.save(user).getId();
    }
}

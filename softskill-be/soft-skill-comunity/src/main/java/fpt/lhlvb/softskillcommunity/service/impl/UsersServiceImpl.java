package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Account;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.user.ChangePasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.ForgotPasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.UserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.FriendResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.UserDetailsResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserDetailsMapping;
import fpt.lhlvb.softskillcommunity.repository.AccountRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.service.MailService;
import fpt.lhlvb.softskillcommunity.service.UsersService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import fpt.lhlvb.softskillcommunity.utils.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ApiResponse getUserDetail() {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        UserDetailsMapping userDetails = usersRepository.getUserDetails(commonService.idUserAccountLogin());

        if (userDetails == null) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        } else {
            userDetailsResponse.setUserDetails(userDetails);
            userDetailsResponse.setPasswordDecrypt(CommonUtils.decrypt(userDetails.getPassword(), CommonConstant.SECRET));

            Optional<Users> u = usersRepository.findByIdAndDeleteFlag(userDetails.getId(), CommonConstant.DELETE_FLAG_FALSE);
            if (u.isPresent()) {
                u.get().setCommonUpdate(commonService.idUserAccountLogin());
                usersRepository.save(u.get());
            }
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, userDetailsResponse);
    }

    @Override
    public ApiResponse getUserDetailById(Long id) {
        Optional<FriendMapping> u = usersRepository.findOtherUserInfo(id, commonService.idUserAccountLogin());
        FriendResponse response = new FriendResponse();
        if (u.isPresent()) {
            response.setId(u.get().getId());
            response.setName(u.get().getName());
            response.setNickname(u.get().getNickName());
            response.setStatus(u.get().getStatus());
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    public ApiResponse searchByMailOrNickName(String req) {

        List<Users> users = usersRepository.findByMailContainsAndDeleteFlag(req, CommonConstant.DELETE_FLAG_FALSE);
        if (users == null || users.size() == 0) {
            users = usersRepository.findByNicknameContainsAndDeleteFlag(req, CommonConstant.DELETE_FLAG_FALSE);
        }
        List<FriendResponse> response = new ArrayList<>();

        for (Users u : users) {
            response.add(new FriendResponse(u.getId(), u.getName(), u.getNickname(), u.getPicture()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse changePassword(ChangePasswordRequest req) {
        Optional<Users> usersOptional = usersRepository.findByIdAndDeleteFlag(
                commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE);
        if (usersOptional.isEmpty()) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }

        String checkOldPassword = checkNullOldPassword(req.getOldPassword());
        if (checkOldPassword != null) {
            return new ApiResponse(CommonConstant.HTTP_CODE_404, CommonConstant.ERROR, checkOldPassword);
        }

        String currentPassword = CommonUtils.decrypt(usersOptional.get().getAccount().getPassword(), CommonConstant.SECRET);

        if (!currentPassword.equals(StringUtils.trimToEmpty(req.getOldPassword()))) {
            Map<String, String> res = new HashMap<>();
            res.put("OLD_PASSWORD", "Not true");
            res.put(CommonConstant.ERROR_MESSAGE, MessageUtils.ERROR_OLD_PASSWORD_DUPLICATE);
            return new ApiResponse(CommonConstant.HTTP_CODE_404, CommonConstant.ERROR, res);
        }

        String checkNewPassword = checkNewPassword(req);
        if (checkNewPassword != null) {
            return new ApiResponse(CommonConstant.HTTP_CODE_404, CommonConstant.ERROR, checkNewPassword);
        }

        Optional<Account> accountOptional = accountRepository.findByUserAndDeleteFlagFalse(usersOptional.get());

        accountOptional.get().setPassword(CommonUtils.encrypt(req.getNewPassword(), CommonConstant.SECRET));
        usersOptional.get().setCommonUpdate(commonService.idUserAccountLogin());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS,
                accountRepository.save(accountOptional.get()).getId());
    }

    public String checkNullOldPassword(String oldPassword) {
        if (StringUtils.trimToEmpty(oldPassword).isEmpty()) {
            return String.format(MessageUtils.ERROR_NULL, "mật khẩu cũ");
        }
        return null;
    }

    private String checkNewPassword(ChangePasswordRequest request) {
        String oldPassword = request.getOldPassword(),
                newPassword = request.getNewPassword(),
                renewPassword = request.getRenewPassword();

        if (StringUtils.trimToEmpty(newPassword).isEmpty()) {
            return String.format(MessageUtils.ERROR_NULL, "mật khẩu mới");
        } else if (!newPassword.matches(CommonConstant.REGEX_PASSWORD)) {
            return MessageUtils.ERROR_PASSWORD;
        } else if (oldPassword.equals(newPassword)) {
            return MessageUtils.ERROR_OLD_AND_NEW_PASSWORD;
        } else if (StringUtils.trimToEmpty(renewPassword).isEmpty()) {
            return String.format(MessageUtils.ERROR_NULL, "xác nhận mật khẩu mới");
        } else if (!renewPassword.equals(newPassword)) {
            return MessageUtils.ERROR_RENEW_PASSWORD;
        }
        return null;
    }

    private String checkNickName(String oldNickname, String nickName, String otherNickname) {
        if (StringUtils.trimToEmpty(nickName).isEmpty()) {
            return String.format(MessageUtils.ERROR_NULL, "nick name");
        } else if (!StringUtils.trimToEmpty(otherNickname).isEmpty()) {
            return MessageUtils.UPDATE_USER_PROFILE_NICKNAME_ERROR_1;
        } else if (!nickName.startsWith("@")) {
            return MessageUtils.UPDATE_USER_PROFILE_NICKNAME_ERROR_2;
        } else if (oldNickname.equals(nickName)) {
            return MessageUtils.UPDATE_USER_PROFILE_NICKNAME_ERROR_3;
        }
        return null;
    }

    @Override
    public ApiResponse changeNickName(Map<String, String> request) {
        String nickName = request.get("nickName");
        String otherNickname = null;
        Optional<Users> usersOptional = usersRepository.findByIdAndDeleteFlag(
                commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE);
        String oldNickname = usersOptional.get().getNickname();

        Optional<Users> otherUser = usersRepository.findByNicknameAndDeleteFlag(nickName, CommonConstant.DELETE_FLAG_FALSE);

        if (otherUser.isPresent()) {
            otherNickname = otherUser.get().getNickname();
        }

        String errorMessage = checkNickName(oldNickname, nickName, otherNickname);
        if (errorMessage != null) {
            return new ApiResponse(CommonConstant.HTTP_CODE_500, CommonConstant.ERROR, errorMessage);
        }

        usersOptional.get().setNickname(nickName);
        usersOptional.get().setCommonUpdate(commonService.idUserAccountLogin());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS,
                usersRepository.save(usersOptional.get()).getId());
    }

    private String checkName(String oldName, String name) {
        if (StringUtils.trimToEmpty(name).isEmpty()) {
            return String.format(MessageUtils.ERROR_NULL, "tên của bạn");
        } else if (oldName.equals(name)) {
            return MessageUtils.UPDATE_USER_PROFILE_NAME_ERROR_1;
        }
        return null;
    }

    public ApiResponse changeName(Map<String, String> req) {
        String name = req.get("name");
        Optional<Users> usersOptional = usersRepository.findByIdAndDeleteFlag(
                commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE);
        String oldName = usersOptional.get().getName();

        String errorMessage = checkName(oldName, name);
        if (errorMessage != null) {
            return new ApiResponse(CommonConstant.HTTP_CODE_500, CommonConstant.ERROR, errorMessage);
        }

        usersOptional.get().setName(name);
        usersOptional.get().setCommonUpdate(commonService.idUserAccountLogin());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS,
                usersRepository.save(usersOptional.get()).getId());
    }

    @Override
    public ApiResponse forgotPassword(ForgotPasswordRequest email) {
        String checkEmail = checkEmail(email.getEmail());
        if (checkEmail != null) {
            return new ApiResponse(CommonConstant.HTTP_CODE_500, CommonConstant.ERROR, checkEmail);
        }

        Optional<Users> usersOptional = usersRepository.findByMailAndDeleteFlag(email.getEmail(), CommonConstant.DELETE_FLAG_FALSE);
        if (usersOptional.isEmpty()) {
            return new ApiResponse(CommonConstant.HTTP_CODE_500, CommonConstant.ERROR, String.format(MessageUtils.ERROR_NOT_EXIST, "email"));
        }

        String newPassword = CommonUtils.randomString(8);
        String password = CommonUtils.encrypt(newPassword.trim(), CommonConstant.SECRET);
        usersOptional.get().getAccount().setPassword(password);
        usersOptional.get().getAccount().setCommonUpdate(usersOptional.get().getId());
        usersRepository.save(usersOptional.get());

        Map<String, Object> resetPassword = new HashMap<>();
        resetPassword.put("to", email.getEmail());
        resetPassword.put("subject", "Reset password from SoftSkill Web");
        resetPassword.put("password", newPassword);

        mailService.sendForgotPasswordMail(resetPassword);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, newPassword);
    }

    private String checkEmail(String email) {
        if (StringUtils.trimToEmpty(email).isEmpty()) {
            return String.format(MessageUtils.ERROR_NULL, "email");
        } else if (!email.matches(CommonConstant.REGEX_EMAIL) || email.contains("..")) {
            return String.format(MessageUtils.ERROR_NOT_VALID, "Email");
        }
        return null;
    }

    public Map<String, Object> updateUser(UserRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        String nickName = request.getNickname();
        String username = request.getUsername();
        Users user = usersRepository.findByIdAndDeleteFlag(userId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        Account account = accountRepository.findByUserIdAndDeleteFlag(userId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        if (!user.getNickname().equals(nickName)) {
            if (usersRepository.findByNicknameAndDeleteFlag(nickName, CommonConstant.DELETE_FLAG_FALSE).isPresent()) {
                response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.UPDATE_USER_PROFILE_NICKNAME_ERROR_1);
                return response;
            } else {
                if (!nickName.startsWith("@")) {
                    response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.UPDATE_USER_PROFILE_NICKNAME_ERROR_2);
                    return response;
                }
            }
        } else if (!account.getUsername().equals(username)) {
            if (accountRepository.findByUsernameAndDeleteFlag(username, CommonConstant.DELETE_FLAG_FALSE).isPresent()) {
//                response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.UPDATE_USER_PROFILE_USERNAME_ERROR);
                return response;
            }
        }

        user.setName(request.getName());
        user.setNickname(nickName);
        user.setGender(request.getGender());
        user.setBirthdate(request.getBirthdate());
        user.setCommonUpdate(userId);
        usersRepository.save(user);

        account.setUsername(username);
        accountRepository.save(account);
        response.put("userId", user.getId());
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.UPDATE_USER_PROFILE_SUCCESS);

        return response;
    }
}

package fpt.lhlvb.softskillcommunity.service;
import fpt.lhlvb.softskillcommunity.model.request.user.ChangePasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.ForgotPasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.UserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface UsersService {
    ApiResponse getUserDetail();

    ApiResponse getUserDetailById(Long id);

    ApiResponse searchByMailOrNickName(String req);

    ApiResponse changePassword(ChangePasswordRequest req);

    ApiResponse changeNickName(Map<String, String> req);

    ApiResponse changeName(Map<String, String> req);

    ApiResponse forgotPassword(ForgotPasswordRequest email);

    Map<String, Object> updateUser(UserRequest request);
}

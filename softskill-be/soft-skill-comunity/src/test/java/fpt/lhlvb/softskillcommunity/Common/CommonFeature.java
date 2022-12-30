package fpt.lhlvb.softskillcommunity.Common;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.LoginRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.ChangePasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.ForgotPasswordRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.securities.UserDetailsServiceImpl;
import fpt.lhlvb.softskillcommunity.securities.jwt.JwtTokenUtils;
import fpt.lhlvb.softskillcommunity.service.AuthenticationService;
import fpt.lhlvb.softskillcommunity.service.UsersService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommonFeature {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UsersService usersService;

    static String passwordDefault = "A1234567";
    static String nameDefault = "longdo";
    static String nickNameDefault = "@longxink";

    @BeforeEach
    void loginRequire() {
        String jwt = authenticationService.login(new LoginRequest("longxink", passwordDefault)).getToken();
        try {
            if (jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {
                String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            logger.error("Cannot set user authentication: " + e);
        }
        logger.info("Login success");
//        logger.info("id Login " + commonService.idUserAccountLogin());
    }

    @Test
    @DisplayName("LoginWithUserName method")
    void loginWithUserName() {
        List<LoginRequest> loginRequests = new ArrayList<>() {
            {
                add(new LoginRequest(null, null));
                add(new LoginRequest(null, "abc"));
                add(new LoginRequest("login", null));
                add(new LoginRequest("\" or \"\"=\"", "\" or \"\"=\""));
                add(new LoginRequest("abc.vinh", "123456"));
                add(new LoginRequest("admin.long", "123456"));
                add(new LoginRequest("longxink", passwordDefault));
            }
        };

//        RecordNotFoundException thrown = assertThrows(RecordNotFoundException.class, () -> {
//            authenticationService.login(loginRequests.get(0));
//        });
//        RecordNotFoundException thrown1 = assertThrows(RecordNotFoundException.class, () -> {
//            authenticationService.login(loginRequests.get(1));
//        });
        RecordNotFoundException thrown2 = assertThrows(RecordNotFoundException.class, () -> {
            authenticationService.login(loginRequests.get(2));
        });
        RecordNotFoundException thrown3 = assertThrows(RecordNotFoundException.class, () -> {
            authenticationService.login(loginRequests.get(3));
        });

        assertAll("login fail",
//                () -> assertEquals("Record Not Found", thrown.getMessage()),
//                () -> assertEquals("Record Not Found", thrown1.getMessage()),
                () -> assertEquals("Record Not Found", thrown2.getMessage()),
                () -> assertEquals("Record Not Found", thrown3.getMessage()));

        assertAll("login success",
                () -> assertNotNull(authenticationService.login(loginRequests.get(6))),
                () -> assertNotNull(authenticationService.login(loginRequests.get(4))),
                () -> assertNotNull(authenticationService.login(loginRequests.get(5))));
    }

    @Test
    @DisplayName("Logout method")
    void logout() {
    }

    @Test
    @DisplayName("Change password method")
    void changePassword() {
        List<ChangePasswordRequest> changePasswordRequests = new ArrayList<>() {
            {
                add(new ChangePasswordRequest(null, "a1234567", "B12345678"));
                add(new ChangePasswordRequest("Abc12345", "a1234567", "B12345678"));
                add(new ChangePasswordRequest(passwordDefault, null, "B12345678"));
                add(new ChangePasswordRequest(passwordDefault, "a1234567", "B12345678"));
                add(new ChangePasswordRequest(passwordDefault, "A1234567", "B12345678"));
                add(new ChangePasswordRequest(passwordDefault, "B1234567", null));
                add(new ChangePasswordRequest(passwordDefault, "B1234567", "B12345678"));
                add(new ChangePasswordRequest(passwordDefault, "B1234567", "B1234567"));
                add(new ChangePasswordRequest("B1234567", passwordDefault, passwordDefault));
            }
        };

        String errorMessageConst = CommonConstant.ERROR;
        String successMessageConst = CommonConstant.SUCCESS;

        ApiResponse apiResponse = usersService.changePassword(changePasswordRequests.get(0));
        assertEquals(errorMessageConst, apiResponse.getMessage());
        logger.info(errorMessageConst + " - " + apiResponse.getItem());

        ApiResponse apiResponse1 = usersService.changePassword(changePasswordRequests.get(1));
        assertEquals(errorMessageConst, apiResponse.getMessage());
        logger.info(errorMessageConst + " - " + apiResponse1.getItem());

        ApiResponse apiResponse2 = usersService.changePassword(changePasswordRequests.get(2));
        assertEquals(errorMessageConst, apiResponse2.getMessage());
        logger.info(errorMessageConst + " - " + apiResponse2.getItem());

        ApiResponse apiResponse3 = usersService.changePassword(changePasswordRequests.get(3));
        assertEquals(errorMessageConst, apiResponse3.getMessage());
        logger.info(errorMessageConst + " - " + apiResponse3.getItem());

        ApiResponse apiResponse4 = usersService.changePassword(changePasswordRequests.get(4));
        assertEquals(errorMessageConst, apiResponse4.getMessage());
        logger.info(errorMessageConst + " - " + apiResponse4.getItem());

        ApiResponse apiResponse5 = usersService.changePassword(changePasswordRequests.get(5));
        assertEquals(errorMessageConst, apiResponse5.getMessage());
        logger.info(errorMessageConst + " - " + apiResponse5.getItem());

        ApiResponse apiResponse6 = usersService.changePassword(changePasswordRequests.get(6));
        assertEquals(errorMessageConst, apiResponse6.getMessage());
        logger.info(errorMessageConst + " - " + apiResponse6.getItem());

        ApiResponse apiResponse7 = usersService.changePassword(changePasswordRequests.get(7));
        assertEquals(successMessageConst, apiResponse7.getMessage());
        logger.info(MessageUtils.CHANGE_PASSWORD_SUCCESS + "\n");

        usersService.changePassword(changePasswordRequests.get(8));
    }

    @Test
    @DisplayName("Forgot password method")
    void forgotPassword() {
        List<ForgotPasswordRequest> forgotPasswordRequests = new ArrayList<>() {
            {
                add(new ForgotPasswordRequest(null));
                add(new ForgotPasswordRequest("abc@gmail"));
                add(new ForgotPasswordRequest("abc.vinh.test@gmail.com"));
                add(new ForgotPasswordRequest("19longdt.fptu@gmail.com"));
            }
        };

        String errorMessageConst = CommonConstant.ERROR;
        String successMessage = CommonConstant.CREATE_SUCCESS;

        ApiResponse errorNull = usersService.forgotPassword(forgotPasswordRequests.get(0));
        assertEquals(errorMessageConst, errorNull.getMessage());
        logger.info(errorMessageConst + " - " + errorNull.getItem());

        ApiResponse errorValid = usersService.forgotPassword(forgotPasswordRequests.get(1));
        assertEquals(errorMessageConst, errorValid.getMessage());
        logger.info(errorMessageConst + " - " + errorValid.getItem());

        ApiResponse errorExists = usersService.forgotPassword(forgotPasswordRequests.get(2));
        assertEquals(errorMessageConst, errorExists.getMessage());
        logger.info(errorMessageConst + " - " + errorExists.getItem());

        ApiResponse success = usersService.forgotPassword(forgotPasswordRequests.get(3));
        assertEquals(successMessage, success.getMessage());
        logger.info(MessageUtils.FORGOT_PASSWORD_SUCCESS + "\n");

        usersService.changePassword(new ChangePasswordRequest(success.getItem().toString(), passwordDefault, passwordDefault));
    }

    @Test
    @DisplayName("Update profile method")
    void updateProfile() {
        String error = CommonConstant.ERROR;
        String success = CommonConstant.SUCCESS;
        String errorMessageConst = CommonConstant.ERROR_MESSAGE;
        String successMessage = CommonConstant.SUCCESS_MESSAGE;
        Map<String, String> request = new HashMap<>();
        List<String> names = new ArrayList<>() {
            {
                add(null);
                add(nameDefault);
                add("tuanlongdo");
            }
        };
        List<String> nickNames = new ArrayList<>() {
            {
                add(null);
                add(nickNameDefault);
                add("longxink");
                add("@longdt19821");
                add("@longxinkxink");
            }
        };

        for (int i = 0; i < names.size() - 1; i++) {
            request.put("name", names.get(i));

            ApiResponse errorResponse = usersService.changeName(request);
            assertEquals(error, errorResponse.getMessage());
            logger.info(errorMessageConst + " - " + errorResponse.getItem());
            request.remove("name");
        }

        for (int i = 0; i < nickNames.size() - 1; i++) {
            request.put("nickName", nickNames.get(i));

            ApiResponse errorResponse = usersService.changeNickName(request);
            assertEquals(error, errorResponse.getMessage());
            logger.info(errorMessageConst + " - " + errorResponse.getItem());
            request.remove("nickName");
        }

        assertAll("success",
                () -> {
                    request.put("name", names.get(names.size() - 1));
                    ApiResponse changeName = usersService.changeName(request);
                    assertEquals(success, changeName.getMessage());
                },
                () -> {
                    request.put("nickName", nickNames.get(nickNames.size() - 1));
                    ApiResponse changeNickname = usersService.changeNickName(request);
                    assertEquals(success, changeNickname.getMessage());
                    logger.info(successMessage + " - " + MessageUtils.UPDATE_USER_PROFILE_SUCCESS);
                });

        request.put("name", nameDefault);
        request.put("nickName", nickNameDefault);
        usersService.changeNickName(request);
        usersService.changeName(request);
    }
}

//cEVT2wnlYtgBcvToQ1xOWA==

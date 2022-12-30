package fpt.lhlvb.softskillcommunity.controller;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.event.OnUserLogoutSuccessEvent;
import fpt.lhlvb.softskillcommunity.model.request.LogOutRequest;
import fpt.lhlvb.softskillcommunity.model.request.LoginRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.ForgotPasswordRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.LoginResponse;
import fpt.lhlvb.softskillcommunity.securities.UserDetailsImpl;
import fpt.lhlvb.softskillcommunity.service.AuthenticationService;
import fpt.lhlvb.softskillcommunity.service.auth.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(URLConstant.AUTH_URL)
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(authenticationService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logoutUser(@CurrentUser UserDetailsImpl currentUser,
                                                  @RequestBody LogOutRequest logOutRequest) {
        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(currentUser.getUsername(),
                logOutRequest.getToken(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
        return ResponseEntity.ok()
                .body(new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.LOGGED_OUT_SUCCESS, null));
    }
}

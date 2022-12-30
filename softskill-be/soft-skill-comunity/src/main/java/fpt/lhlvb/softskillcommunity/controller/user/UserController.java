package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.ChangePasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.ForgotPasswordRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RegisterUserRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.UserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.securities.UserDetailsImpl;
import fpt.lhlvb.softskillcommunity.service.AuthenticationService;
import fpt.lhlvb.softskillcommunity.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(URLConstant.USER_URL)
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity.ok().body(authenticationService.registerUser(request));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getUserDetails() {
        return ResponseEntity.ok().body(usersService.getUserDetail());
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse> searchByMailOrNickName(@RequestParam String req) {
        return ResponseEntity.ok().body(usersService.searchByMailOrNickName(req));
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse> getUserInfo(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(usersService.getUserDetail());
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ApiResponse> getOtherUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getUserDetailById(id));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> updateChangePassword(@RequestBody @Valid ChangePasswordRequest req) {
        return ResponseEntity.ok().body(usersService.changePassword(req));
    }

    @PutMapping("/change-nickname")
    public ResponseEntity<ApiResponse> updateChangeNickname(@RequestBody Map<String, String> req) {
        return ResponseEntity.ok().body(usersService.changeNickName(req));
    }

    @PutMapping("/change-name")
    public ResponseEntity<ApiResponse> updateChangeName(@RequestBody Map<String, String> req) {
        return ResponseEntity.ok().body(usersService.changeName(req));
    }

    @PostMapping("/forgot")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok().body(usersService.forgotPassword(request));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserRequest request) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, usersService.updateUser(request));
        return ResponseEntity.ok().body(apiResponse);
    }
}

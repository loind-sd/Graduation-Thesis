package fpt.lhlvb.softskillcommunity.controller.admin;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.AccountService;
import fpt.lhlvb.softskillcommunity.service.InviteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.ACCOUNT_ADMIN_URL)
public class AccountAdminController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private InviteUserService inviteUserService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAccInfo() {
        return ResponseEntity.ok().body(accountService.getAccInfo());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addUser(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(inviteUserService.addInvite(req));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateAcc(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(accountService.updateAccInfo(req));
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse> blockUser(@RequestBody Long[] ids) {
        return ResponseEntity.ok().body(accountService.blockUser(ids));
    }
}

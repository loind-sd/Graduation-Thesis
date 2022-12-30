package fpt.lhlvb.softskillcommunity.controller;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.SEND_MAIL_URL)
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/demo")
    public ResponseEntity<ApiResponse> sendMail(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(mailService.sendSimpleMessage(req));
    }

    @PostMapping("/invite")
    public ResponseEntity<ApiResponse> sendInviteMail(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(mailService.sendInviteMail(req));
    }

    @PostMapping("/thank")
    public ResponseEntity<ApiResponse> sendThankMail(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(mailService.sendThankMail(req));
    }
}

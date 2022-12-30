package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface MailService {
    ApiResponse sendSimpleMessage(Map<String, Object> req);

    ApiResponse sendInviteMail(Map<String, Object> req);

    ApiResponse sendThankMail(Map<String, Object> req);

    ApiResponse sendForgotPasswordMail(Map<String, Object> req);

    ApiResponse sendInviteAdmin(Map<String, Object> req);

    ApiResponse sendInitPasswordMail(Map<String, Object> req);
}

package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.MailService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import fpt.lhlvb.softskillcommunity.utils.MailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender emailSender;

    private Map<String, Object> response;

    @Override
    public ApiResponse sendSimpleMessage(Map<String, Object> req) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            StringBuilder content = new StringBuilder();
            content.append("<a href='fb.com'>" + "<Button>" + "Vào đây đi anh!" + "</Button>" + "</a>");
            content.append("/n Em yêu anh nhìu nắm <3");
            mimeMessageHelper.setFrom(sender.trim());
            mimeMessageHelper.setTo(String.valueOf(req.get("to")).trim());
            mimeMessageHelper.setSubject(String.valueOf(req.get("subject")).trim());
            mimeMessageHelper.setText(content.toString(), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse sendInviteMail(Map<String, Object> req) {
        try {
            Map<String, Object> response = new HashMap<>();
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(sender.trim());
            mimeMessageHelper.setTo(String.valueOf(req.get("to")).trim());
            mimeMessageHelper.setSubject(String.valueOf(req.get("subject")).trim());
            mimeMessageHelper.setText(MailTemplate.getMailTemplateInviteJoinWebPage(), true);
            emailSender.send(mimeMessage);

            response.put("send mail", "done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse sendThankMail(Map<String, Object> req) {
        try {
            Map<String, Object> response = new HashMap<>();
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(sender.trim());
            mimeMessageHelper.setTo(String.valueOf(req.get("to")).trim());
            mimeMessageHelper.setSubject(String.valueOf(req.get("subject")).trim());
            mimeMessageHelper.setText(MailTemplate.getMailTemplateThank(String.valueOf(req.get("mailContent"))), true);
            emailSender.send(mimeMessage);

            response.put("send mail", "done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse sendForgotPasswordMail(Map<String, Object> req) {
        try {
            Map<String, Object> response = new HashMap<>();
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(sender.trim());
            mimeMessageHelper.setTo(String.valueOf(req.get("to")).trim());
            mimeMessageHelper.setSubject(String.valueOf(req.get("subject")).trim());
            mimeMessageHelper.setText(MailTemplate.resetPasswordMail(String.valueOf(req.get("password"))), true);
            emailSender.send(mimeMessage);

            response.put("send mail", "done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse sendInviteAdmin(Map<String, Object> req) {
        try {
            Map<String, Object> response = new HashMap<>();
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(sender.trim());
            mimeMessageHelper.setTo(String.valueOf(req.get("to")).trim());
            mimeMessageHelper.setSubject(String.valueOf(req.get("subject")).trim());
            mimeMessageHelper.setText(MailTemplate.inviteAdminMail(String.valueOf(req.get("role"))), true);
            emailSender.send(mimeMessage);

            response.put("send mail", "done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse sendInitPasswordMail(Map<String, Object> req) {
        try {
            Map<String, Object> response = new HashMap<>();
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(sender.trim());
            mimeMessageHelper.setTo(String.valueOf(req.get("to")).trim());
            mimeMessageHelper.setSubject(String.valueOf(req.get("subject")).trim());
            mimeMessageHelper.setText(MailTemplate.sendInitPasswordMail(String.valueOf(req.get("password"))), true);
            emailSender.send(mimeMessage);

            response.put("send mail", "done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }
}


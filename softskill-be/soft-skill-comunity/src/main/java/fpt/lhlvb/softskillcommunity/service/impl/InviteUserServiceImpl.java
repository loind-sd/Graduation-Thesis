package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.InviteUser;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.repository.InviteUserRepository;
import fpt.lhlvb.softskillcommunity.service.InviteUserService;
import fpt.lhlvb.softskillcommunity.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InviteUserServiceImpl implements InviteUserService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private InviteUserRepository inviteUserRepository;

    @Autowired
    private MailService mailService;

    @Override
    public ApiResponse addInvite(Map<String, Object> req) {
        if (inviteUserRepository.findByMailOver7Days(String.valueOf(req.get("mail"))).isPresent()) {
            return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.ERROR_MESSAGE, CommonConstant.ERROR_MESSAGE);
        }
        if (String.valueOf(req.get("mail")).matches(CommonConstant.REGEX_EMAIL)) {
            InviteUser inviteUser = new InviteUser();
            inviteUser.setMail(String.valueOf(req.get("mail")));
            inviteUser.setRole(String.valueOf(req.get("role")));
            inviteUser.setCommonRegister(commonService.idUserAccountLogin());

            inviteUserRepository.save(inviteUser);
            Map<String, Object> map = new HashMap<>();
            map.put("to", String.valueOf(req.get("mail")));
            map.put("subject", "V/v Tham gia cùng chúng tôi");
            map.put("role", String.valueOf(req.get("role")).equals("ADMIN") ? "Quản trị viên" : String.valueOf(req.get("role")).equals("AUTHOR") ? "Quản lý" : "");
            mailService.sendInviteAdmin(map);
            return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, CommonConstant.CREATE_SUCCESS);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, CommonConstant.ERROR_MESSAGE);
    }

    @Override
    public ApiResponse deleteInvite(InviteUser inviteUser) {
        inviteUser.setCommonDelete(1L);
        inviteUserRepository.save(inviteUser);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }
}

package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.entity.InviteUser;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface InviteUserService {
    ApiResponse addInvite(Map<String, Object> req);

    ApiResponse deleteInvite(InviteUser inviteUser);
}

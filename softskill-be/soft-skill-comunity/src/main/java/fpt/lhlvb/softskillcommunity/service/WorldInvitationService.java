package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface WorldInvitationService {
    ApiResponse loadInvitation(Map<String, Object> req);

    ApiResponse addInvitation(Long roomId);
    public ApiResponse deleteMessage(Long id);

}

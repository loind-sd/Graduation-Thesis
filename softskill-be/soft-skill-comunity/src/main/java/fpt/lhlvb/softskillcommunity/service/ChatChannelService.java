package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface ChatChannelService {
    ApiResponse getAllContact();

    ApiResponse addNewContact(Map<String, Object> req);

    ApiResponse getFriendToAdd(Map<String, Object> req);

    ApiResponse addMember(Map<String, Object> req);

    ApiResponse getAllMemberInRoom(Map<String, Object> req);

    ApiResponse kickUser(Map<String, Object> req);
}

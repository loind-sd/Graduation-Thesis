package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface UserRoomInfoService {
    ApiResponse getUserRecent();

    ApiResponse addUserRoomInfo(Map<String, Object> req, int type);

    ApiResponse updateUserRoomInfo(Map<String, Object> req);
}

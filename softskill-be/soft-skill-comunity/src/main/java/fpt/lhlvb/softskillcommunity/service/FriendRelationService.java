package fpt.lhlvb.softskillcommunity.service;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.FriendResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface FriendRelationService {
    ApiResponse getAllFriend();
    ApiResponse getAllFriendEvenRequest();
    ApiResponse addFriend(Long id);

    ApiResponse actionFriend(@RequestBody Map<String, Object> req);

    ApiResponse searchToAddFriend(String search);

    ApiResponse deleteFriend(Long id);
}

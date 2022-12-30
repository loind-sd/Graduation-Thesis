package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.request.user.UserTaskOtherRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface UsersRoomTaskService {

    Map<String, Object> getUsersRoomTaskFavouriteOther(UserTaskOtherRequest request);

    Map<String, Object> getUsersRoomTaskOtherByCondition(UserTaskOtherRequest request);

    Map<String, Object> getUsersRoomTaskOtherNotStart(UserTaskOtherRequest request);
}

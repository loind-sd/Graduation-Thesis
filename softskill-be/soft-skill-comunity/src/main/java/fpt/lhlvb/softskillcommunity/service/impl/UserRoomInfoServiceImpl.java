package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Notification;
import fpt.lhlvb.softskillcommunity.entity.UserRoomInfo;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.FriendResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import fpt.lhlvb.softskillcommunity.repository.NotificationRepository;
import fpt.lhlvb.softskillcommunity.repository.NotificationTypeRepository;
import fpt.lhlvb.softskillcommunity.repository.UserRoomInfoRepository;
import fpt.lhlvb.softskillcommunity.service.NotificationService;
import fpt.lhlvb.softskillcommunity.service.UserRoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserRoomInfoServiceImpl implements UserRoomInfoService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRoomInfoRepository userRoomInfoRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Override
    public ApiResponse getUserRecent() {
        Optional<UserRoomInfo> userRoomInfo = userRoomInfoRepository.getRecentRoom(
                commonService.idUserAccountLogin(),
                CommonConstant.DELETE_FLAG_FALSE,
                CommonConstant.JOIN_STATUS_TRUE,
                CommonConstant.LIMIT_1);

        if (userRoomInfo.isEmpty()) {
            return new ApiResponse(CommonConstant.HTTP_CODE_404, CommonConstant.ERROR, null);
        }

        List<FriendMapping> users = userRoomInfoRepository.getUsersInRoom(
                userRoomInfo.get().getRoomId(),
                CommonConstant.DELETE_FLAG_FALSE,
                CommonConstant.JOIN_STATUS_TRUE,
                commonService.idUserAccountLogin());

        List<FriendResponse> response = new ArrayList<>();
        for (FriendMapping u : users) {
            response.add(new FriendResponse(u.getId(), u.getName(), u.getNickName(), u.getPicture(),u.getStatus()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    // type = 1 --> add khi user tự join room (joinStatus = TRUE)
    // type = 2 --> add khi user được mời thông qua notification (joinStatus = FALSE -- chưa join room)
    public ApiResponse addUserRoomInfo(Map<String, Object> req, int type) {
        Map<String, Object> response = new HashMap<>();
        UserRoomInfo info = new UserRoomInfo();
        info.setUserId(Long.parseLong(String.valueOf(req.get("userId"))));
        info.setRoomId(Long.parseLong(String.valueOf(req.get("roomId"))));
        if (CommonConstant.NUMBER_1 == type) {
            info.setJoinStatus(CommonConstant.JOIN_STATUS_TRUE);
            info.setIsJoined(CommonConstant.IS_JOINED_TRUE);
        } else if (CommonConstant.NUMBER_2 == type) {
            info.setJoinStatus(CommonConstant.JOIN_STATUS_FALSE);
            info.setIsJoined(CommonConstant.IS_JOINED_FALSE);
            // add notification
            Map<String, Object> reqNotification = new HashMap<>();
            reqNotification.put("typeId", CommonConstant.NUMBER_2);
            reqNotification.put("userId", Long.parseLong(String.valueOf(req.get("userId"))));
            reqNotification.put("senderId", Long.parseLong(String.valueOf(req.get("roomId"))));
            response.putAll(notificationService.addNotification(reqNotification));
        }
        info.setCommonRegister(commonService.idUserAccountLogin());

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS,
                userRoomInfoRepository.save(info).getId());
    }

    @Override
    public ApiResponse updateUserRoomInfo(Map<String, Object> req) {
        UserRoomInfo info = userRoomInfoRepository.findByUserIdAndRoomIdAndDeleteFlag(
                        commonService.idUserAccountLogin(),
                        Long.parseLong(String.valueOf(req.get("roomId"))),
                        CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        // accept join room
        if (CommonConstant.ACTION_ACCEPT.equals(String.valueOf(req.get("action")))) {
            info.setJoinStatus(CommonConstant.JOIN_STATUS_TRUE);
            info.setIsJoined(CommonConstant.IS_JOINED_TRUE);
            info.setCommonUpdate(commonService.idUserAccountLogin());
        }
        // deny join room -- delete
        else if (CommonConstant.ACTION_DENY.equals(String.valueOf(req.get("action")))) {
            info.setCommonDelete(commonService.idUserAccountLogin());
        }

        Notification notification = notificationRepository.findBySenderIdAndUserIdAndStatusAndDeleteFlagAndNotificationType(Long.parseLong(String.valueOf(req.get("roomId"))), commonService.idUserAccountLogin(), CommonConstant.JOIN_STATUS_FALSE, CommonConstant.DELETE_FLAG_FALSE, notificationTypeRepository.findByIdAndDeleteFlag(Long.valueOf(CommonConstant.NOTIFICATION_INVITE_ROOM), CommonConstant.DELETE_FLAG_FALSE).get()).get();
        Map<String, Object> response = new HashMap<>(notificationService.updateNotificationStatus(notification));
//        response.put("updateUserRoomInfo", "done");
//        response.put("id", );
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS,
                userRoomInfoRepository.save(info).getId());
    }
}

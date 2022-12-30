package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.model.request.user.UserTaskOtherRequest;
import fpt.lhlvb.softskillcommunity.model.response.user.UserRoomTaskOtherResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserRoomTaskOtherMapping;
import fpt.lhlvb.softskillcommunity.repository.FriendRelationRepository;
import fpt.lhlvb.softskillcommunity.repository.UserRoomTaskInfoRepository;
import fpt.lhlvb.softskillcommunity.service.UsersRoomTaskService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersRoomTaskServiceImpl implements UsersRoomTaskService {
    @Autowired
    private FriendRelationRepository friendRelationRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRoomTaskInfoRepository userRoomTaskInfoRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Map<String, Object> getUsersRoomTaskFavouriteOther(UserTaskOtherRequest request) {
        Long userId = commonService.idUserAccountLogin();
        List<UserRoomTaskOtherMapping> usersTaskOther =
                userRoomTaskInfoRepository.getOthersFavouriteByTaskId(request.getSoftSkillId(), userId, request.getTaskId());

        return getFriendsByAll(usersTaskOther, CommonConstant.STATUS_TASK_NOT_START);
    }

    @Override
    public Map<String, Object> getUsersRoomTaskOtherByCondition(UserTaskOtherRequest request) {
        Long userId = commonService.idUserAccountLogin();
        List<UserRoomTaskOtherMapping> usersTaskOther =
                userRoomTaskInfoRepository.getUsersRoomTaskOtherByTaskIdAndCondition(userId, request.getTaskId(),
                        request.getSoftSkillId(), request.getStatusTask());

        return getFriendsByAll(usersTaskOther, request.getStatusTask());
    }

    @Override
    public Map<String, Object> getUsersRoomTaskOtherNotStart(UserTaskOtherRequest request) {
        Long userId = commonService.idUserAccountLogin();
        List<UserRoomTaskOtherMapping> usersTaskOther =
                userRoomTaskInfoRepository.getUsersRoomTaskOtherNotStartByTaskId(userId, request.getTaskId(),
                        CommonConstant.STATUS_TASK_DOING, CommonConstant.STATUS_TASK_COMPLETED);

        return getFriendsByAll(usersTaskOther, request.getStatusTask());
    }

    private Map<String, Object> getFriendsByAll(List<UserRoomTaskOtherMapping> usersTaskOther, Integer status) {
        Map<String, Object> result = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();

        List<FriendMapping> allFriends =
                friendRelationRepository.getAllFriend(CommonConstant.STATUS_IS_FRIEND, CommonConstant.DELETE_FLAG_FALSE, userId);
        List<Long> idUserFriends = allFriends.stream().map(FriendMapping::getId).collect(Collectors.toList());

        List<UserRoomTaskOtherMapping> allFriendsTask = usersTaskOther.stream()
                .filter(o -> idUserFriends.contains(o.getUserId()))
                .collect(Collectors.toList());

        List<UserRoomTaskOtherMapping> allUsersTask = usersTaskOther.stream()
                .filter(o -> !idUserFriends.contains(o.getUserId()))
                .collect(Collectors.toList());

        result.put("allFriendsTask", setUserRoomTaskOtherResponse(allFriendsTask, status));
        result.put("allUsersTask", setUserRoomTaskOtherResponse(allUsersTask, status));
        return result;
    }

    private List<UserRoomTaskOtherResponse> setUserRoomTaskOtherResponse(List<UserRoomTaskOtherMapping> usersMapping, Integer status) {
        List<UserRoomTaskOtherResponse> response = new ArrayList<>();

        for (UserRoomTaskOtherMapping user : usersMapping) {
            UserRoomTaskOtherResponse userRoomTaskOtherResponse = new UserRoomTaskOtherResponse();
            userRoomTaskOtherResponse.setUserRoomTaskOtherMapping(user);

            if (user.getJoinTime() != null) {
                Map<String, Object> resultCompareTwoDate = CommonUtils.compareTwoDate(user.getJoinTime(), CommonUtils.getDateNow());
                int year = (int) resultCompareTwoDate.get("year");
                int month = (int) resultCompareTwoDate.get("month");
                int day = (int) resultCompareTwoDate.get("day");

                if (year == 0) {
                    if (month == 0) {
                        if (Objects.equals(status, CommonConstant.STATUS_TASK_DOING)) {
                            userRoomTaskOtherResponse.setJoinedTimeAgo(String.format(CommonConstant.USER_TASK_TIME_DAY_DOING, day));
                        } else {
                            userRoomTaskOtherResponse.setJoinedTimeAgo(String.format(CommonConstant.USER_TASK_TIME_DAY_DONE, day));
                        }
                    } else {
                        if (Objects.equals(status, CommonConstant.STATUS_TASK_DOING)) {
                            userRoomTaskOtherResponse.setJoinedTimeAgo(String.format(CommonConstant.USER_TASK_TIME_MONTH_DOING, month));
                        } else {
                            userRoomTaskOtherResponse.setJoinedTimeAgo(String.format(CommonConstant.USER_TASK_TIME_MONTH_DONE, month));
                        }
                    }
                } else {
                    if (Objects.equals(status, CommonConstant.STATUS_TASK_DOING)) {
                        userRoomTaskOtherResponse.setJoinedTimeAgo(String.format(CommonConstant.USER_TASK_TIME_YEAR_DOING, year));
                    } else {
                        userRoomTaskOtherResponse.setJoinedTimeAgo(String.format(CommonConstant.USER_TASK_TIME_YEAR_DONE, year));
                    }
                }
            }
            response.add(userRoomTaskOtherResponse);
        }

        return response;
    }
}

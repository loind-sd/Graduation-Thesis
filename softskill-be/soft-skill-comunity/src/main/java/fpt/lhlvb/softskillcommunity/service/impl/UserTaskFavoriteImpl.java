package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Tasks;
import fpt.lhlvb.softskillcommunity.entity.UserTaskFavorite;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import fpt.lhlvb.softskillcommunity.repository.FriendRelationRepository;
import fpt.lhlvb.softskillcommunity.repository.TasksRepository;
import fpt.lhlvb.softskillcommunity.repository.UserTaskFavoriteRepository;
import fpt.lhlvb.softskillcommunity.service.UserTaskFavoriteService;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTaskFavoriteImpl implements UserTaskFavoriteService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserTaskFavoriteRepository favoriteRepository;

    @Autowired
    private FriendRelationRepository friendRelationRepository;

    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public Map<String, Object> getTasksFavoriteFriendsByTaskId(Long taskId) {
        List<UserTaskFavorite> allFriendTasksFavorite;
        List<UserTaskFavorite> allUserTasksFavorite;

        Long userId = commonService.idUserAccountLogin();
        List<FriendMapping> allFriends
                = friendRelationRepository.getAllFriend(CommonConstant.STATUS_IS_FRIEND, CommonConstant.DELETE_FLAG_FALSE, userId);

        List<Long> idUserFriends = allFriends.stream().map(FriendMapping::getId).collect(Collectors.toList());
        List<UserTaskFavorite> allUsersTasksFavorite
                = favoriteRepository.findByTaskIdAndDeleteFlag(taskId, CommonConstant.DELETE_FLAG_FALSE);

        allFriendTasksFavorite = allUsersTasksFavorite.stream()
                .filter(e -> idUserFriends.contains(e.getCreatedBy()))
                .collect(Collectors.toList());

        allUserTasksFavorite = allUsersTasksFavorite.stream()
                .filter(e -> !idUserFriends.contains(e.getCreatedBy()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("allFriendTasksFavorite", allFriendTasksFavorite);
        response.put("allUserTasksFavorite", allUserTasksFavorite);

        return response;
    }

    @Override
    public Map<String, Object> addTaskFavorite(Long taskId, Integer statusId) {
        UserTaskFavorite userTaskFavorite;
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();

        tasksRepository.findByIdAndDeleteFlag(taskId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        userTaskFavorite = favoriteRepository.findByTaskIdAndCreatedByAndDeleteFlag(taskId, userId, CommonConstant.DELETE_FLAG_FALSE);

        if (userTaskFavorite == null) {
            userTaskFavorite = new UserTaskFavorite();
            userTaskFavorite.setCommonRegister(userId);
            userTaskFavorite.setFavorite(true);
            userTaskFavorite.setTaskId(taskId);
            userTaskFavorite.setStatusId(statusId);

            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.ADD_TASK_FAVOURITE);
        } else {
            userTaskFavorite.setCommonUpdate(userId);
            userTaskFavorite.setStatusId(statusId);
            userTaskFavorite.setFavorite(false);

            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.REMOVE_TASK_FAVOURITE);
        }
        favoriteRepository.save(userTaskFavorite);
        return response;
    }
}

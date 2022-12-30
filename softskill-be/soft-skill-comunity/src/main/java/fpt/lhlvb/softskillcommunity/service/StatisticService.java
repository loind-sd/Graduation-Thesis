package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.entity.SoftSkills;
import fpt.lhlvb.softskillcommunity.model.request.user.SoftSkillRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.StatisticForUserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface StatisticService {
    Map<String, Object> statisticTaskForUser(StatisticForUserRequest request);

    Map<String, Object> statisticSoftSkillForUser();

    Map<String, Object> statisticTotalTimeRoomForUser(StatisticForUserRequest request);

    Map<String, Object> statisticRankForUser(StatisticForUserRequest request);

    ApiResponse statisticRankForAdmin();

    ApiResponse countNumberUsers();

    ApiResponse countTotalRooms();

    ApiResponse countNumberJoin(Map<String, Object> req);

    ApiResponse countNumberRoom(Map<String, Object> req);

    ApiResponse countBySoftSkill();
}

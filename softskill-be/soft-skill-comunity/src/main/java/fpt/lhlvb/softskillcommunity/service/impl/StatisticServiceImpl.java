package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.RankInfo;
import fpt.lhlvb.softskillcommunity.entity.Statistic;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.user.StatisticForUserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.admin.StatisticRankResponse;
import fpt.lhlvb.softskillcommunity.model.response.admin.StatisticRoomResponse;
import fpt.lhlvb.softskillcommunity.model.response.admin.mapping.CountMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.StatisticRankUserResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.StatisticTaskResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.CountNumberJoinMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.StatisticRankUserMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.StatisticTaskMapping;
import fpt.lhlvb.softskillcommunity.repository.RankInfoRepository;
import fpt.lhlvb.softskillcommunity.repository.StatisticRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.service.StatisticService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RankInfoRepository rankInfoRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYYMMDD);

    @Override
    public Map<String, Object> statisticTaskForUser(StatisticForUserRequest request) {
        Long userId = null;
        if (request.getUserId() == null) {
            userId = commonService.idUserAccountLogin();
        } else {
            userId = request.getUserId();
        }

        String[] monthYear = request.getTime().split("-");

        int endDate = CommonUtils.getEndDateInMonthYear(monthYear);

        Map<String, Object> response = new HashMap<>();
        List<StatisticTaskResponse> statisticTaskResponses = new ArrayList<>();
        int count = 1;
        String fromDateString = null;
        String toDateString = null;
        Date fromDate = null;
        Date toDate = null;
        StatisticTaskResponse statisticTaskResponse = null;
        StatisticTaskMapping taskMapping;

        try {
            for (int i = 0; i < endDate; i += 7) {
                if (count <= 3) {
                    fromDateString = monthYear[0] + "/" + monthYear[1] + "/" + (i + 1);
                    toDateString = monthYear[0] + "/" + monthYear[1] + "/" + (i + 7);
                } else if (count == 4) {
                    fromDateString = monthYear[0] + "/" + monthYear[1] + "/" + (i + 1);
                    toDateString = monthYear[0] + "/" + monthYear[1] + "/" + endDate;
                } else {
                    response.put("list", statisticTaskResponses);
                    break;
                }
                fromDate = sdf.parse(fromDateString);
                toDate = sdf.parse(toDateString);

                statisticTaskResponse = new StatisticTaskResponse();
                statisticTaskResponse.setFromDate(sdf.format(fromDate));
                statisticTaskResponse.setToDate(sdf.format(toDate));

                taskMapping = statisticRepository.getStatisticTaskForUser(userId, fromDate, toDate);
                if (taskMapping != null) {
                    statisticTaskResponse.setStatisticTaskMapping(taskMapping);
                } else {
                    statisticTaskResponse.setStatisticTaskMapping(null);
                }
                statisticTaskResponses.add(statisticTaskResponse);
                count += 1;
            }
        } catch (ParseException e) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        return response;
    }

    @Override
    public Map<String, Object> statisticSoftSkillForUser() {
        Map<String, Object> response = new HashMap<>();
        response.put("list", statisticRepository.countBySoftSkill());
        return response;
    }

    @Override
    public Map<String, Object> statisticTotalTimeRoomForUser(StatisticForUserRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId;
        if (request.getUserId() == null) {
            userId = commonService.idUserAccountLogin();
        } else {
            userId = request.getUserId();
        }
        response.put("totalTimeRoom", statisticRepository.countTimeRoomForUser(userId));
        return response;
    }

    @Override
    public Map<String, Object> statisticRankForUser(StatisticForUserRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId;
        if (request.getUserId() == null) {
            userId = commonService.idUserAccountLogin();
        } else {
            userId = request.getUserId();
        }
        StatisticRankUserResponse userResponse = new StatisticRankUserResponse();
        int count = 0;

        StatisticRankUserMapping rankUserMapping = statisticRepository.getStatisticRankUser(userId);
        List<RankInfo> rankInfos = rankInfoRepository.findAllByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);

        if (rankUserMapping == null) {
            userResponse.setConditionRank(rankInfos.get(0).getConditionTask());
            userResponse.setCompletedTask(0);
            userResponse.setCompletedTime(0);
            userResponse.setRank(rankInfos.get(0).getName());
        } else {
            userResponse.setCompletedTask(rankUserMapping.getCompletedTask());
            userResponse.setCompletedTime(rankUserMapping.getCompletedTime());
            for (RankInfo rankInfo : rankInfos) {
                count += 1;
                if (rankInfo.getConditionTask() > rankUserMapping.getCompletedTask() && count < rankInfos.size()) {
                    userResponse.setConditionRank(rankInfo.getConditionTask());
                    userResponse.setRank(rankInfo.getName());
                    break;
                } else if (count == rankInfos.size()) {
                    userResponse.setConditionRank(rankInfos.get(rankInfos.size() - 1).getConditionTask());
                    userResponse.setRank(rankInfos.get(rankInfos.size() - 1).getName());
                }
            }
        }

        response.put("rankInfo", userResponse);
        return response;
    }

    @Override
    public ApiResponse statisticRankForAdmin() {
        List<StatisticRankResponse> response = new ArrayList<>();
        List<StatisticRankUserMapping> statistics = statisticRepository.getStatisticRankForAdmin();
        List<RankInfo> rankInfos = rankInfoRepository.findAllByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);

        for (int i = 0; i < rankInfos.size(); i++) {
            int count = CommonConstant.STATUS_0;
            for (StatisticRankUserMapping statistic : statistics) {
                if (i == 0) {
                    if (statistic.getCompletedTask() < rankInfos.get(i).getConditionTask()) {
                        count++;
//                        statistics.remove(statistic);
                    }
                } else if (i != rankInfos.size() - 1) {
                    if (statistic.getCompletedTask() >= rankInfos.get(i - 1).getConditionTask() &&
                            statistic.getCompletedTask() < rankInfos.get(i).getConditionTask()) {
                        count++;
//                        statistics.remove(statistic);
                    }
                } else {
                    if (statistic.getCompletedTask() >= rankInfos.get(i).getConditionTask()) {
                        count++;
//                        statistics.remove(statistic);
                    }
                }
            }
            response.add(new StatisticRankResponse(rankInfos.get(i).getName(), count));
        }
        int num = CommonConstant.STATUS_0;
        for (int j = 1; j < response.size(); j++) {
            num += response.get(j).getNumber();
        }
        int sum = ((CountMapping) countNumberUsers().getItem()).getNumber();
        response.get(0).setNumber(sum - num);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse countNumberUsers() {
        String date = getLastDayOfPreviousMonth();
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticRepository.countNumberUsers(java.sql.Date.valueOf(date)).get());
    }

    private String getLastDayOfPreviousMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        String[] now = sdf.format(CommonUtils.resultTimestamp()).split("-");
        String date = null;
        if ("1".equals(now[1])) {
            now[1] = "12";
            now[0] = String.valueOf(Integer.parseInt(now[0]) - 1);
        } else {
            now[1] = String.valueOf(Integer.parseInt(now[1]) - 1);
        }
        int endDate = CommonUtils.getEndDateInMonthYear(now);
        date = now[0] + '-' + now[1] + '-' + endDate;
        return date;
    }

    @Override
    public ApiResponse countTotalRooms() {
        String date = getLastDayOfPreviousMonth();
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticRepository.countTotalRooms(java.sql.Date.valueOf(date)).get());
    }

    @Override
    public ApiResponse countNumberJoin(Map<String, Object> req) {
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticRepository.countNumberJoin(java.sql.Date.valueOf(String.valueOf(req.get("time")))));
    }

    @Override
    public ApiResponse countNumberRoom(Map<String, Object> req) {
        String[] monthYear = String.valueOf(req.get("time")).split("-");
        List<StatisticRoomResponse> response = new ArrayList<>();
        int endDate = CommonUtils.getEndDateInMonthYear(monthYear);
        String fromDateString = null;
        String toDateString = null;
        String week = null;
        Date fromDate = null;
        Date toDate = null;

        for (int i = 0; i < endDate; i += 7) {
            if (i + 7 < endDate) {
                fromDateString = monthYear[0] + "/" + monthYear[1] + "/" + (i + 1);
                toDateString = monthYear[0] + "/" + monthYear[1] + "/" + (i + 7);
                week = String.valueOf((i + 1)) + '-' + (i + 7) + "/" + monthYear[1];
            } else {
                fromDateString = monthYear[0] + "/" + monthYear[1] + "/" + (i + 1);
                toDateString = monthYear[0] + "/" + monthYear[1] + "/" + endDate;
                week = String.valueOf((i + 1)) + '-' + endDate + "/" + monthYear[1];
            }

            try {
                fromDate = sdf.parse(fromDateString);
                toDate = sdf.parse(toDateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            response.add(new StatisticRoomResponse(
                    week, statisticRepository.countNumberActiveRoom(fromDate, toDate).get(), statisticRepository.countNumberBookingRoom(fromDate, toDate).get()
            ));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse countBySoftSkill() {
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticRepository.countBySoftSkill());
    }
}

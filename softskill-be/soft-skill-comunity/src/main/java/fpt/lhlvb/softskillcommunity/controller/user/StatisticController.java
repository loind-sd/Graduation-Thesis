package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.StatisticForUserRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URLConstant.STATISTIC_USER_URL)
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/task")
    public ResponseEntity<ApiResponse> statisticTaskForUser(@RequestBody StatisticForUserRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticService.statisticTaskForUser(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/softSkill")
    public ResponseEntity<ApiResponse> statisticSoftSkillForUser(){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticService.statisticSoftSkillForUser());
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/totalTimeRoom")
    public ResponseEntity<ApiResponse> statisticTotalTimeRoomForUser(@RequestBody StatisticForUserRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticService.statisticTotalTimeRoomForUser(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/rank")
    public ResponseEntity<ApiResponse> statisticRankForUser(@RequestBody StatisticForUserRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, statisticService.statisticRankForUser(request));
        return ResponseEntity.ok().body(apiResponse);
    }
}

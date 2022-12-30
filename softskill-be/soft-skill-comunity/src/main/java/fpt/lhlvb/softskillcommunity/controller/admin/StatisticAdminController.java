package fpt.lhlvb.softskillcommunity.controller.admin;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.STATISTIC_ADMIN_URL)
public class StatisticAdminController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/numberUsers")
    public ResponseEntity<ApiResponse> countNumberUsers(){
        return ResponseEntity.ok().body(statisticService.countNumberUsers());
    }

    @GetMapping("/totalRooms")
    public ResponseEntity<ApiResponse> countTotalRooms(){
        return ResponseEntity.ok().body(statisticService.countTotalRooms());
    }

    @PostMapping("/numberJoin")
    public ResponseEntity<ApiResponse> countNumberJoin(@RequestBody Map<String, Object> req){
        return ResponseEntity.ok().body(statisticService.countNumberJoin(req));
    }

    @PostMapping("/numberRoom")
    public ResponseEntity<ApiResponse> countNumberRoom(@RequestBody Map<String, Object> req){
        return ResponseEntity.ok().body(statisticService.countNumberRoom(req));
    }

    @GetMapping("/softSkill")
    public ResponseEntity<ApiResponse> countBySoftSkill(){
        return ResponseEntity.ok().body(statisticService.countBySoftSkill());
    }

    @GetMapping("/rank")
    public ResponseEntity<ApiResponse> statisticRankForAdmin(){
        return ResponseEntity.ok().body(statisticService.statisticRankForAdmin());
    }
}

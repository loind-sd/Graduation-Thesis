package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.RecommendTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import java.io.IOException;

@RestController
@RequestMapping(URLConstant.RECOMMEND_TASK_URL)
public class RecommendTaskController {

    @Autowired
    private RecommendTaskService recommendTaskService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getRecommendTask() {
        return ResponseEntity.ok().body(recommendTaskService.getRecommendTaskForUser());
    }

    @GetMapping("/checkTime")
    public ResponseEntity<ApiResponse> getCheckTimeAddRecommendTask() {
        return ResponseEntity.ok().body(recommendTaskService.getCheckTimeAddRecommendTask());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addRecommendTask(
            @Nullable @RequestParam("fileTask") MultipartFile fileTask,
            @Nullable @RequestParam("fileGuideline") MultipartFile fileGuideline,
            @RequestParam("softSkillId") Integer softSkillId,
            @RequestParam("typeTask") Integer typeTask,
            @RequestParam("taskDescription") String taskDescription,
            @RequestParam("guideline") String guideline) throws IOException {
        return ResponseEntity.ok().body(recommendTaskService.addRecommendTask(fileTask,fileGuideline, softSkillId, typeTask, taskDescription, guideline));
    }
}

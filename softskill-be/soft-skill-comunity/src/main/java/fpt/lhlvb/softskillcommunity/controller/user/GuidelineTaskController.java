package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.GuidelineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstant.GUIDELINE_TASK_URL)
public class GuidelineTaskController {
    @Autowired
    private GuidelineTaskService guidelineTaskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> getGuidelineByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok().body(guidelineTaskService.getGuidelineByTaskId(taskId));
    }
}

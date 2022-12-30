
package fpt.lhlvb.softskillcommunity.controller.manager;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.RecommendTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.RECOMMEND_TASK_AUTHOR_URL)
public class ManageRecommendTaskController {
    @Autowired
    private RecommendTaskService recommendTaskService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getRecommendTaskInfo() {
        return ResponseEntity.ok().body(recommendTaskService.getRecommendTaskInfo());
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateRecommendTaskInfo(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(recommendTaskService.updateRecommendTaskStatus(req));
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse> deleteRecommendTaskInfo(@RequestBody Long[] ids) {
        return ResponseEntity.ok().body(recommendTaskService.deleteRecommendTaskInfo(ids));
    }
}

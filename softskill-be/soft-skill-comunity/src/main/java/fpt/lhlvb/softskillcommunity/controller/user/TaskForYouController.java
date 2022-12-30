package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.TaskForYouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.TASK_FOR_YOU_URL)
public class TaskForYouController {

    @Autowired
    private TaskForYouService taskForYouService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getTaskForYou() {
        return ResponseEntity.ok().body(taskForYouService.getTaskForYou());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addTaskForYou(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(taskForYouService.addTaskForYou(req));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateTaskForYou(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(taskForYouService.updateTaskForYou(req));

    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteTaskForYou(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(taskForYouService.deleteTaskForYou(req));
    }
}

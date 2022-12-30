package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.UpdateFavoriteTaskRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(URLConstant.TASK_URL)
public class TaskController {

    @Autowired
    private TasksService tasksService;

    @GetMapping("/search/{taskName}")
    public ResponseEntity<ApiResponse> searchTaskByName(@PathVariable String taskName) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, tasksService.searchTaskByName(taskName));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> getByUserIdAndTaskId(@PathVariable Long taskId) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, tasksService.getByUserIdAndTaskId(taskId));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok().body(tasksService.getTaskById(id));
    }

    @PostMapping("/update-favorite")
    public ResponseEntity<ApiResponse> updateFavoriteTaskById(@RequestBody @Valid UpdateFavoriteTaskRequest req) {
        return ResponseEntity.ok().body(tasksService.updateFavoriteTaskById(req));
    }
}

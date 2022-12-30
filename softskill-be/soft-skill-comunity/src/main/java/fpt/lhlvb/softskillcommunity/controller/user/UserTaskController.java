package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.UserTaskRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(URLConstant.USER_TASK_URL)
public class UserTaskController {
    @Autowired
    private UserTaskService userTaskService;

    @GetMapping("/{statusId}")
    public ResponseEntity<ApiResponse> getUserTasks(@PathVariable @NotNull Integer statusId) {

        return ResponseEntity.ok().body(userTaskService.getAllUserTasks(statusId));
    }

    @PutMapping("/updateStatus/{taskId}")
    public ResponseEntity<ApiResponse> updateStatusToCompleted(@PathVariable Long taskId) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                userTaskService.updateStatusUserTask(taskId));

        return ResponseEntity.ok().body(apiResponse);
    }
}

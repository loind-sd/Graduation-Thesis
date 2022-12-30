package fpt.lhlvb.softskillcommunity.controller.manager;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.manager.AddTaskRequest;
import fpt.lhlvb.softskillcommunity.model.request.manager.ManagerTaskRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.RecommendTaskService;
import fpt.lhlvb.softskillcommunity.service.TasksService;
import fpt.lhlvb.softskillcommunity.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(URLConstant.MANAGER_TASK_URL)
public class ManagerTaskController {

    @Autowired
    private TasksService tasksService;

    @Autowired
    private UserTaskService userTaskService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getTasksBySoftSkillAndTime(@RequestBody ManagerTaskRequest managerTaskRequest) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                tasksService.getAllTaskBySoftSkillAndTimeForManager(managerTaskRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/group-task")
    public ResponseEntity<ApiResponse> getGroupTaskInfo() {
        return ResponseEntity.ok().body(tasksService.getGroupTaskInfo());
    }

    @PutMapping("/group-task")
    public ResponseEntity<ApiResponse> updateGroupTaskInfo(@Nullable @RequestParam("fileTask") MultipartFile fileTask,
                                                           @Nullable @RequestParam("fileGuideline") MultipartFile fileGuideline,
                                                           @RequestParam("guideline") String guideline,
                                                           @RequestParam("softSkillId") Integer softSkillId,
                                                           @RequestParam("taskName") String taskName,
                                                           @RequestParam("taskContent") String taskContent,
                                                           @RequestParam("requiredTask") Integer requiredTask,
                                                           @RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate,
                                                           @RequestParam("status") String status,
                                                           @RequestParam("id") Integer id,
                                                           @RequestParam("no") Integer no,
                                                           @RequestParam("guideLineChange") String guideLineChange) throws IOException {
        return ResponseEntity.ok().body(tasksService.updateGroupTask(fileTask, fileGuideline, softSkillId, taskName, taskContent, requiredTask, startDate, endDate, guideline, status, id, guideLineChange, no));
    }

    @PostMapping("/group-task")
    public ResponseEntity<ApiResponse> addGroupTaskInfo(@RequestParam("fileTask") MultipartFile fileTask,
                                                        @Nullable @RequestParam("fileGuideline") MultipartFile fileGuideline,
                                                        @RequestParam("softSkillId") Integer softSkillId,
                                                        @RequestParam("taskName") String taskName,
                                                        @RequestParam("taskContent") String taskContent,
                                                        @RequestParam("requiredTask") Integer requiredTask,
                                                        @RequestParam("startDate") String startDate,
                                                        @RequestParam("endDate") String endDate,
                                                        @RequestParam("guideline") String guideline,
                                                        @RequestParam("status") String status) throws IOException {
        return ResponseEntity.ok().body(tasksService.addGroupTask(fileTask, fileGuideline, softSkillId, taskName, taskContent, requiredTask, startDate, endDate, guideline, status));
    }

    @PostMapping("/group-task/delete")
    public ResponseEntity<ApiResponse> deleteGroupTaskInfo(@RequestBody Long[] ids) {
        return ResponseEntity.ok().body(tasksService.deleteGroupTask(ids));
    }

    @GetMapping("/individual-task")
    public ResponseEntity<ApiResponse> getIndividualTaskInfo() {
        return ResponseEntity.ok().body(userTaskService.getIndividualTaskInfo());
    }

    @PutMapping("/individual-task")
    public ResponseEntity<ApiResponse> updateIndividualTaskInfo(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(userTaskService.updateIndividualTask(req));
    }

    @PostMapping("/individual-task")
    public ResponseEntity<ApiResponse> addIndividualTaskInfo(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(userTaskService.addIndividualTask(req));
    }

    @PostMapping("/individual-task/delete")
    public ResponseEntity<ApiResponse> deleteIndividualTaskInfo(@RequestBody Long[] ids) {
        return ResponseEntity.ok().body(userTaskService.deleteIndividualTask(ids));
    }
}

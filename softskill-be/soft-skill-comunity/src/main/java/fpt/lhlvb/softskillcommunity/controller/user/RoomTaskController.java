package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.*;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.RoomTaskService;
import fpt.lhlvb.softskillcommunity.service.TasksService;
import fpt.lhlvb.softskillcommunity.service.UserTaskService;
import fpt.lhlvb.softskillcommunity.service.UsersRoomTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;

@RestController
@RequestMapping(URLConstant.ROOM_TASK_URL)
public class RoomTaskController {
    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private UsersRoomTaskService usersRoomTaskService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private RoomTaskService roomTaskService;

    /**
     * Home screen
     */
    @PostMapping("/registerRoomTask")
    public ResponseEntity<ApiResponse> registerRoomTask(@RequestBody @Valid RegisterRoomTaskRequest request) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.registerRoomTask(request));

        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * Task screen
     */
    @GetMapping("/notStart/{softSkillId}")
    public ResponseEntity<ApiResponse> getTaskNotStart(@PathVariable Long softSkillId) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, tasksService.getTasksNotStartBySoftSkillId(softSkillId));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/notStart")
    public ResponseEntity<ApiResponse> getAllTaskNotStart() {
        return ResponseEntity.ok().body(tasksService.getAllTasksNotStart());
    }

    @GetMapping("/allWithCondition/{statusId}")
    public ResponseEntity<ApiResponse> getAllTaskByCondition(@PathVariable Integer statusId) {
        return ResponseEntity.ok().body(tasksService.getAllTaskByCondition(statusId));
    }

    @GetMapping("/condition")
    public ResponseEntity<ApiResponse> getTaskByCondition(@RequestBody @Valid TaskRequest taskRequest) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, tasksService.getTasksByCondition(taskRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/favorite")
    public ResponseEntity<ApiResponse> getTasksFavourite() {
        return ResponseEntity.ok().body(tasksService.getTasksFavorite());
    }

    // list friends or community with status in task view
    @PostMapping("/othersFavourite")
    public ResponseEntity<ApiResponse> getUsersTaskOtherFavourite(@RequestBody UserTaskOtherRequest request) {
        ApiResponse apiResponse =
                new ApiResponse(CommonConstant.HTTP_CODE_200,
                        CommonConstant.SUCCESS,
                        usersRoomTaskService.getUsersRoomTaskFavouriteOther(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/othersNotStart")
    public ResponseEntity<ApiResponse> getUsersTaskOtherNotStart(@RequestBody UserTaskOtherRequest request) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                usersRoomTaskService.getUsersRoomTaskOtherNotStart(request));

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/others")
    public ResponseEntity<ApiResponse> getUsersTaskOtherByCondition(@RequestBody UserTaskOtherRequest request) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                usersRoomTaskService.getUsersRoomTaskOtherByCondition(request));

        return ResponseEntity.ok().body(apiResponse);
    }

//    // update statusUserRoomTask ở màn task
//    @PutMapping("/doing/{taskId}")
//    public ResponseEntity<ApiResponse> updateStatusUserRoomTask(@PathVariable Long taskId) {
//        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
//                CommonConstant.CREATE_SUCCESS,
//                userTaskService.updateStatusUserRoomTaskToCompleted(taskId));
//
//        return ResponseEntity.ok().body(apiResponse);
//    }

    /**
     * RoomMeeting screen
     */
    @PostMapping("/vote/{roomId}")
    public ResponseEntity<ApiResponse> voteRoomTask(@PathVariable Long roomId) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.voteRoomTask(roomId));

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/acceptDoRoomTask")
    public ResponseEntity<ApiResponse> acceptDoRoomTask(@RequestBody RoomTaskActionRequest request) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.acceptDoRoomTask(request));

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/joinRoom/{roomId}")
    public ResponseEntity<ApiResponse> getRoomTaskMeetingDetail(@PathVariable Long roomId) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.getRoomTaskMeetingDetail(roomId));

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/outRoom")
    public ResponseEntity<ApiResponse> outRoomMeeting(@RequestBody RoomTaskActionRequest request) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.outRoomMeeting(request));

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/tasks")
    public ResponseEntity<ApiResponse> getAllTasksBySoftSkillAndTime(@RequestBody @Valid TaskRequest taskRequest) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                tasksService.getAllTaskBySoftSkillAndTimeForUser(taskRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<ApiResponse> getTaskDetailsByTaskId(@PathVariable Long taskId) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                tasksService.getTaskDetailsInRoom(taskId));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/submitTasks")
    public ResponseEntity<ApiResponse> submitTask(@RequestBody @Valid RoomTaskActionRequest request) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.submitTask(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/changeLockRoom/{roomId}")
    public ResponseEntity<ApiResponse> changeLockRoom(@PathVariable Long roomId) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.changeLockRoom(roomId));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/completed")
    public ResponseEntity<ApiResponse> completedRoomTask(@RequestBody @Valid RoomTaskActionRequest roomTasksRequest) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.completedRoomTask(roomTasksRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/cancel")
    public ResponseEntity<ApiResponse> cancelRoomTask(@RequestBody @Valid RoomTaskActionRequest roomTasksRequest) {
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.cancelRoomTask(roomTasksRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/members")
    public ResponseEntity<ApiResponse> getUserRoomTaskInfo(@RequestBody RoomTaskActionRequest roomTasksRequest){
        ApiResponse apiResponse
                = new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS,
                roomTaskService.getUserRoomTaskInfo(roomTasksRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/reset-task-info/{taskId}")
    public ResponseEntity<ApiResponse> resetUserRoomTaskInfo(@PathVariable Long taskId){
        return ResponseEntity.ok().body(roomTaskService.resetUserRoomTaskInfo(taskId));
    }
}

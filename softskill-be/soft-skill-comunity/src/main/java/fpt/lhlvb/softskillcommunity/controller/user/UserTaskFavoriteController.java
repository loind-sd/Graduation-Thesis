package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.UserTaskFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstant.USER_TASK_FAVORITE_URL)
public class UserTaskFavoriteController {
    @Autowired
    private UserTaskFavoriteService userTaskFavoriteService;

    @PostMapping("/{taskId}/{statusId}")
    public ResponseEntity<ApiResponse> addTaskFavorite(@PathVariable Long taskId, @PathVariable Integer statusId) {
        ApiResponse apiResponse =
                new ApiResponse(CommonConstant.HTTP_CODE_200,
                        CommonConstant.SUCCESS,
                        userTaskFavoriteService.addTaskFavorite(taskId, statusId));
        return ResponseEntity.ok().body(apiResponse);
    }
}

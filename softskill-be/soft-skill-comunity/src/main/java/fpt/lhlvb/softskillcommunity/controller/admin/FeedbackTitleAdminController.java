package fpt.lhlvb.softskillcommunity.controller.admin;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.admin.FeedbackTitleRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.FeedbackTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URLConstant.FEEDBACK_TITLE_ADMIN_URL)
public class FeedbackTitleAdminController {

    @Autowired
    private FeedbackTitleService feedbackTitleService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAll() {
        try {
            ApiResponse response = new ApiResponse(
                    CommonConstant.HTTP_CODE_200,
                    CommonConstant.SUCCESS,
                    feedbackTitleService.getAll());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addFeedbackTitle(@RequestBody FeedbackTitleRequest req) {
        try {
            ApiResponse response = new ApiResponse(
                    CommonConstant.HTTP_CODE_200,
                    CommonConstant.CREATE_SUCCESS,
                    feedbackTitleService.addFeedbackTitle(req));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateFeedbackTitle(@RequestBody FeedbackTitleRequest req) {
        try {
            ApiResponse response = new ApiResponse(
                    CommonConstant.HTTP_CODE_200,
                    CommonConstant.CREATE_SUCCESS,
                    feedbackTitleService.updateFeedbackTitle(req));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteFeedbackTitle(@RequestBody FeedbackTitleRequest req) {
        try {
            ApiResponse response = new ApiResponse(
                    CommonConstant.HTTP_CODE_200,
                    CommonConstant.DELETE_SUCCESS,
                    feedbackTitleService.deleteFeedbackTitle(req));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }
}

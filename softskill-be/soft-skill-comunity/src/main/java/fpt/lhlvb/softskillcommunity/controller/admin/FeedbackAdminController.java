package fpt.lhlvb.softskillcommunity.controller.admin;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.admin.FeedbackRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URLConstant.FEEDBACK_ADMIN_URL)
public class FeedbackAdminController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping()
    public ResponseEntity<ApiResponse> loadFeedback() {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, feedbackService.loadFeedback());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> confirmFeedback(@RequestBody FeedbackRequest request){
        return ResponseEntity.ok().body(feedbackService.confirmFeedback(request));
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<ApiResponse> deleteFeedback(@PathVariable Long feedbackId){
        return ResponseEntity.ok().body(feedbackService.deleteFeedback(feedbackId));
    }
}

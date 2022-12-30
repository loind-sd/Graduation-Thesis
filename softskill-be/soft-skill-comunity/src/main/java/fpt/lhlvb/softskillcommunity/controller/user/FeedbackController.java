package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.FeedbackRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.FEEDBACK_URL)
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping()
    public ResponseEntity<ApiResponse> sentFeedback(@RequestBody FeedbackRequest request) {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, feedbackService.sentFeedback(request));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }
}

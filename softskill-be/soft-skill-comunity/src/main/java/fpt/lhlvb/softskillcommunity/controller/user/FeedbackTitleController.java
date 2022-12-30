package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.FeedbackTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstant.FEEDBACK_TITLE_URL)
public class FeedbackTitleController {

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
}

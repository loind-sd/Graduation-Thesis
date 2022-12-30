package fpt.lhlvb.softskillcommunity.service;
import fpt.lhlvb.softskillcommunity.model.request.admin.FeedbackTitleRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.FeedbackTitleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface FeedbackTitleService {
    ApiResponse getAll();
    ApiResponse addFeedbackTitle(FeedbackTitleRequest req);
    ApiResponse updateFeedbackTitle(FeedbackTitleRequest req);
    ApiResponse deleteFeedbackTitle(FeedbackTitleRequest req);

}

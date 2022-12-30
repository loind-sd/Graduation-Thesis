package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.request.admin.FeedbackRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FeedbackMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface FeedbackService {
    List<FeedbackMapping> loadFeedback();

    Map<String, Object> sentFeedback(fpt.lhlvb.softskillcommunity.model.request.user.FeedbackRequest req);

    ApiResponse confirmFeedback(FeedbackRequest request);

    ApiResponse deleteFeedback(Long feedbackId);
}

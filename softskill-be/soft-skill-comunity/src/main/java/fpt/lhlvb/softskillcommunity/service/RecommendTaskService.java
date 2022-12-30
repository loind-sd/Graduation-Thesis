package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.RecommendTaskResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface RecommendTaskService {
    List<RecommendTaskResponse> getRecommendTask();

    ApiResponse getRecommendTaskForUser();

    ApiResponse updateRecommendTaskStatus(Map<String, Object> req);

    ApiResponse addRecommendTask(MultipartFile fileTask,MultipartFile fileGuideline, Integer softSkillId, Integer typeTask, String taskDescription, String guideline) throws IOException;

    ApiResponse getCheckTimeAddRecommendTask();
    ApiResponse getRecommendTaskInfo();

    ApiResponse deleteRecommendTaskInfo(Long[] ids);
}

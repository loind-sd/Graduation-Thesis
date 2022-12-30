package fpt.lhlvb.softskillcommunity.service;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface TaskRelationService {
    ApiResponse getTaskBySoftSkillId(Long softSkillId);

    ApiResponse getRecommendTaskType();
}

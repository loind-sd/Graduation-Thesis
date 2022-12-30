package fpt.lhlvb.softskillcommunity.service.impl;
import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.DropdownResponse;
import fpt.lhlvb.softskillcommunity.repository.RecommendTasTypeRepository;
import fpt.lhlvb.softskillcommunity.repository.TaskRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fpt.lhlvb.softskillcommunity.service.TaskRelationService;

import java.util.List;

@Service
public class TaskRelationServiceImpl implements TaskRelationService {

    @Autowired
    private TaskRelationRepository taskRelationRepository;

    @Autowired
    private RecommendTasTypeRepository recommendTasTypeRepository;
    @Override
    public ApiResponse getTaskBySoftSkillId(Long softSkillId) {
        List<DropdownResponse> response = taskRelationRepository.getBySoftSkillId(softSkillId);
        return new ApiResponse(CommonConstant.HTTP_CODE_200,CommonConstant.SUCCESS,response);
    }

    @Override
    public ApiResponse getRecommendTaskType() {
        List<DropdownResponse> response = recommendTasTypeRepository.dropdownRecommendTaskType();
        return new ApiResponse(CommonConstant.HTTP_CODE_200,CommonConstant.SUCCESS,response);
    }
}

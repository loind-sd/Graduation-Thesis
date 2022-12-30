package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.RecommendTask;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.RecommendTaskContentMapping;
import fpt.lhlvb.softskillcommunity.model.response.manager.RecommendTaskInfoResponse;
import fpt.lhlvb.softskillcommunity.model.response.manager.mapping.RecommendTaskInfoMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.RecommendTaskResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.RecommendTaskUserResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RecommentTaskMapping;
import fpt.lhlvb.softskillcommunity.repository.RecommendTasTypeRepository;
import fpt.lhlvb.softskillcommunity.repository.RecommentTaskRepository;
import fpt.lhlvb.softskillcommunity.repository.SoftSkillsRepository;
import fpt.lhlvb.softskillcommunity.service.RecommendTaskService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendTaskServiceImpl implements RecommendTaskService {

    @Autowired
    private RecommentTaskRepository recommentTaskRepository;

    @Autowired
    private RecommendTasTypeRepository recommendTasTypeRepository;

    @Autowired
    private SoftSkillsRepository softSkillsRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public List<RecommendTaskResponse> getRecommendTask() {
        List<RecommendTaskResponse> result = new ArrayList<>();
        List<RecommentTaskMapping> mapping = recommentTaskRepository.getRecommendTask(CommonConstant.DELETE_FLAG_FALSE);

        for (RecommentTaskMapping m : mapping) {
            result.add(new RecommendTaskResponse(m.getId(), m.getContent(), m.getTypeName(), m.getName(), m.getNickName(), m.getMail(), m.getSoftSkillName(), m.getStatusId(), m.getStatusName()));
        }
        return result;
    }

    @Override
    public ApiResponse getRecommendTaskForUser() {
        List<RecommendTaskUserResponse> result = new ArrayList<>();
        List<RecommentTaskMapping> mapping = recommentTaskRepository.getRecommendTaskForUser(CommonConstant.DELETE_FLAG_FALSE, commonService.idUserAccountLogin());

        for (RecommentTaskMapping m : mapping) {
            result.add(new RecommendTaskUserResponse(m.getId(), new RecommendTaskContentMapping(m.getContent(), m.getDateCreate()),
                    m.getTypeName(), m.getSoftSkillName(), m.getStatusName(), m.getGuideline(), m.getTaskImage(), m.getGuidelineImage()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, result);
    }

    public ApiResponse responseAfterAdd(Long id, MultipartFile fileTask, MultipartFile fileGuideline, Integer softSkillId, Integer typeTask, String taskDescription, String guideline) throws IOException {
        RecommendTaskUserResponse response = new RecommendTaskUserResponse();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_DDMMYYYY);
        response.setId(id);
        response.setContent(new RecommendTaskContentMapping(taskDescription, sdf.format(CommonUtils.resultTimestamp())));
        response.setTypeName(recommendTasTypeRepository.findByIdAndDeleteFlag(Long.valueOf(typeTask), CommonConstant.DELETE_FLAG_FALSE).get().getContent());
        response.setSoftSkillName(softSkillsRepository.findByIdAndDeleteFlag(Long.valueOf(softSkillId), CommonConstant.DELETE_FLAG_FALSE).get().getName());
        response.setStatus("Chờ duyệt");
        response.setGuideline(guideline);
        response.setTaskImage((fileTask == null || fileTask.isEmpty()) ? null : fileTask.getBytes());
        response.setGuidelineImage((fileGuideline == null || fileGuideline.isEmpty()) ? null : fileGuideline.getBytes());

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse updateRecommendTaskStatus(Map<String, Object> req) {
        RecommendTask recommendTask = recommentTaskRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("id"))), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        recommendTask.setStatus(Integer.parseInt(String.valueOf(req.get("status"))));
        recommendTask.setCommonUpdate(commonService.idUserAccountLogin());

        Map<String, Object> response = new HashMap<>();
        response.put("updateRecommendTaskStatus", "done");
        response.put("id", recommentTaskRepository.save(recommendTask).getId());

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse addRecommendTask(MultipartFile fileTask, MultipartFile fileGuideline, Integer softSkillId, Integer typeTask, String taskDescription, String guideline)
            throws IOException {
        RecommendTask recommendTask = new RecommendTask();
        recommendTask.setContent(taskDescription);
        recommendTask.setGuideline(guideline);
        recommendTask.setTypeId(typeTask);
        recommendTask.setSoftSkillId(softSkillId);
        recommendTask.setStatus(CommonConstant.STATUS_1);
        recommendTask.setImageData((fileTask == null || fileTask.isEmpty()) ? null : fileTask.getBytes());
        recommendTask.setGuidelineImage((fileGuideline == null || fileGuideline.isEmpty()) ? null : fileGuideline.getBytes());
        recommendTask.setCommonRegister(commonService.idUserAccountLogin());

        return responseAfterAdd(recommentTaskRepository.save(recommendTask).getId(), fileTask, fileGuideline, softSkillId, typeTask, taskDescription, guideline);
    }

    @Override
    public ApiResponse getCheckTimeAddRecommendTask() {
        Optional<RecommendTask> recommendTask = recommentTaskRepository.getRecommendTaskByUserId(commonService.idUserAccountLogin());
        if (recommendTask.isEmpty()) {
            return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, 0);
        } else {
            Date date = new Date();
            Integer time = Math.toIntExact(2 * 24 * 60 * 60 * 1000 - (date.getTime() - recommendTask.get().getCreatedAt().getTime()));

            return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, time);
        }
    }

    @Override
    public ApiResponse getRecommendTaskInfo() {
        List<RecommendTaskInfoMapping> mappings = recommentTaskRepository.getRecommendTaskInfo();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_DDMMYYYY);
        List<RecommendTaskInfoResponse> response = new ArrayList<>();
        int i = CommonConstant.NUMBER_1;
        for (RecommendTaskInfoMapping m : mappings) {
            response.add(new RecommendTaskInfoResponse(
                    m.getId(), i++, m.getCreatedName(), m.getTaskContent(), m.getSoftSkillName(),
                    m.getTaskType(), m.getStatus(), sdf.format(m.getCreatedDate()), m.getUpdateName(), m.getGuideline(),
                    m.getTaskImage(), m.getGuidelineImage(), m.getMail()
            ));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse deleteRecommendTaskInfo(Long[] ids) {
        for (Long id : ids) {
            RecommendTask recommendTask = recommentTaskRepository.findByIdAndDeleteFlag(id, CommonConstant.DELETE_FLAG_FALSE)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

            recommendTask.setCommonDelete(commonService.idUserAccountLogin());
            recommentTaskRepository.save(recommendTask);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS, null);
    }
}

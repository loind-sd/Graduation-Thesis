package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.FeedbackTitle;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.admin.FeedbackTitleRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.FeedbackTitleResponse;
import fpt.lhlvb.softskillcommunity.repository.FeedbackTitleRepository;
import fpt.lhlvb.softskillcommunity.service.FeedbackTitleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackTitleServiceImpl implements FeedbackTitleService {

    @Autowired
    private FeedbackTitleRepository feedbackTitleRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ApiResponse getAll() {
        List<FeedbackTitle> feedbackTitleList = feedbackTitleRepository.findAllByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        List<FeedbackTitleResponse> response = new ArrayList<>();

        for (FeedbackTitle feedbackTitle : feedbackTitleList) {
            response.add(modelMapper.map(feedbackTitle, FeedbackTitleResponse.class));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse addFeedbackTitle(FeedbackTitleRequest req) {
        Map<String, Object> response = new HashMap<>();
        FeedbackTitle feedbackTitle = new FeedbackTitle();
        feedbackTitle.setName(req.getName());
        feedbackTitle.setCommonRegister(commonService.idUserAccountLogin());

        Long id = feedbackTitleRepository.save(feedbackTitle).getId();
        response.put("id", id);
        response.put("add", "done");

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse updateFeedbackTitle(FeedbackTitleRequest req) {
        Map<String, Object> response = new HashMap<>();
        FeedbackTitle feedbackTitle = feedbackTitleRepository.findByIdAndDeleteFlag(req.getId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        feedbackTitle.setName(req.getName());
        feedbackTitle.setCommonUpdate(commonService.idUserAccountLogin());

        Long id = feedbackTitleRepository.save(feedbackTitle).getId();
        response.put("id", id);
        response.put("update", "done");

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse deleteFeedbackTitle(FeedbackTitleRequest req) {
        Map<String, Object> response = new HashMap<>();
        FeedbackTitle feedbackTitle = feedbackTitleRepository.findByIdAndDeleteFlag(req.getId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        feedbackTitle.setName(req.getName());
        feedbackTitle.setCommonDelete(commonService.idUserAccountLogin());

        Long id = feedbackTitleRepository.save(feedbackTitle).getId();
        response.put("id", id);
        response.put("delete", "done");

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS, response);
    }
}

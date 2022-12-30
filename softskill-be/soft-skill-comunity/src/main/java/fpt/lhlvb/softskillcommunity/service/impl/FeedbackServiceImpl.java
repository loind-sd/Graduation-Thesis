package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Feedback;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.admin.FeedbackRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FeedbackMapping;
import fpt.lhlvb.softskillcommunity.repository.FeedbackRepository;
import fpt.lhlvb.softskillcommunity.repository.FeedbackTitleRepository;
import fpt.lhlvb.softskillcommunity.service.FeedbackService;
import fpt.lhlvb.softskillcommunity.service.MailService;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackTitleRepository feedbackTitleRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private CommonService commonService;

    @Override
    public List<FeedbackMapping> loadFeedback() {
        List<FeedbackMapping> mapping = feedbackRepository.loadFeedback();
        System.out.println(mapping);
        return mapping;
    }

    public String checkFeedbackRequest(String rateStar, String titleId, String content) {

        try {
            Integer rate = Integer.parseInt(rateStar);
            if (rate < CommonConstant.MIN_RATE_STAR || rate > CommonConstant.MAX_RATE_STAR) {
                return MessageUtils.ERROR_RATE_STAR;
            }
        } catch (Exception e) {
            return String.format(MessageUtils.ERROR_NOT_VALID, "Đánh giá sao");
        }

        if (titleId == null) {
            return String.format(MessageUtils.ERROR_NULL, "loại câu hỏi");
        }

        try {
            Long title = Long.parseLong(titleId);
            if (feedbackTitleRepository.findByIdAndDeleteFlag(title, CommonConstant.DELETE_FLAG_FALSE).isEmpty()) {
                return String.format(MessageUtils.ERROR_NOT_EXIST, "loại câu hỏi");
            }
        } catch (Exception e) {
            return String.format(MessageUtils.ERROR_NOT_VALID, "Loại câu hỏi");
        }

        if (content == null) {
            return String.format(MessageUtils.ERROR_NULL, "nội dung");
        } else if (content.length() > CommonConstant.MAX_FEEDBACK_CONTENT ) {
            return String.format(MessageUtils.ERROR_STRING_LENGTH, "Nội dung", CommonConstant.MAX_FEEDBACK_CONTENT);
        }

        return null;
    }

    @Override
    public Map<String, Object> sentFeedback(fpt.lhlvb.softskillcommunity.model.request.user.FeedbackRequest request) {
        Map<String, Object> response = new HashMap<>();
        Feedback feedback = new Feedback();

        String titleId = request.getTitleId();
        String rateStar = request.getRateStar();
        String content = request.getContent();

        String validate = checkFeedbackRequest(rateStar, titleId, content);
        if (validate != null) {
            response.put(CommonConstant.ERROR_MESSAGE, validate);
            return response;
        }

        feedback.setContent(content);
        feedback.setTitleId(Long.parseLong(titleId));
        feedback.setRateStar(Integer.parseInt(rateStar));
        feedback.setCommonRegister(commonService.idUserAccountLogin());
        Long id = feedbackRepository.save(feedback).getId();

        response.put("id", id);
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.ADD_FEEDBACK_SUCCESS);
        return response;
    }

    @Override
    public ApiResponse confirmFeedback(FeedbackRequest request) {
        Map<String, Object> confirm = new HashMap<>();
        Long adminId = commonService.idUserAccountLogin();

        Feedback feedback = feedbackRepository.findByIdAndDeleteFlag(request.getFeedbackId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        feedback.setStatus(true);
        feedback.setContentResponse(request.getContentResponse());
        feedback.setCommonUpdate(adminId);
        feedbackRepository.save(feedback);

        confirm.put("to", request.getEmail());
        confirm.put("subject", MessageUtils.CONFIRM_FEEDBACK_SUBJECT);
        confirm.put("mailContent", request.getContentResponse());
        mailService.sendThankMail(confirm);

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS_MESSAGE, feedback);
    }

    @Override
    public ApiResponse deleteFeedback(Long feedbackId) {
        Long adminId = commonService.idUserAccountLogin();

        Feedback feedback = feedbackRepository.findByIdAndDeleteFlag(feedbackId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        feedback.setCommonDelete(adminId);
        feedbackRepository.save(feedback);

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS_MESSAGE, feedback);
    }
}
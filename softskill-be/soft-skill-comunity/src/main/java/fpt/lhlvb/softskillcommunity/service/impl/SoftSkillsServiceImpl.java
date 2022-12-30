package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.SoftSkills;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.user.SoftSkillRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.ComboboxResponse;
import fpt.lhlvb.softskillcommunity.model.response.admin.SoftSkillInfoResponse;
import fpt.lhlvb.softskillcommunity.model.response.admin.mapping.SoftSkillInfoMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.StatisticSoftSkillMapping;
import fpt.lhlvb.softskillcommunity.repository.StatisticRepository;
import fpt.lhlvb.softskillcommunity.service.StatisticService;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fpt.lhlvb.softskillcommunity.repository.SoftSkillsRepository;
import fpt.lhlvb.softskillcommunity.service.SoftSkillsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SoftSkillsServiceImpl implements SoftSkillsService {

    @Autowired
    private SoftSkillsRepository softSkillsRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public List<SoftSkills> getAllSoftSkill() {
        return softSkillsRepository.findByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
    }

    @Override
    public List<ComboboxResponse> getSoftSkills() {
        List<SoftSkills> softSkills = getAllSoftSkill();
        List<ComboboxResponse> responses = new ArrayList<>();

        for (SoftSkills s : softSkills) {
            responses.add(new ComboboxResponse() {
                @Override
                public Long getValue() {
                    return s.getId();
                }

                @Override
                public String getLabel() {
                    return s.getName();
                }
            });
        }
        return responses;
    }

    @Override
    public ApiResponse getSoftSkillInfo() {
        List<SoftSkillInfoMapping> mappings = softSkillsRepository.getInfo();
        List<SoftSkillInfoResponse> response = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_DDMMYYYY);
        int i = CommonConstant.NUMBER_1;

        for (SoftSkillInfoMapping m : mappings) {
            response.add(new SoftSkillInfoResponse(
                    m.getId(), i++, m.getSoftSkillName(), m.getNumberRoom(), m.getCountTaskCompleted(), m.getStatus(), sdf.format(m.getCreatedDate()), m.getCreatedName()
            ));
        }

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public Map<String, Object> addSoftSkill(SoftSkillRequest request) {
        SoftSkills softSkills = new SoftSkills();
        softSkills.setName(request.getSoftSkillName());
        softSkills.setCommonRegister(commonService.idUserAccountLogin());
        if ("Dừng".equals(request.getStatus())) {
            softSkills.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
        }
        softSkillsRepository.save(softSkills);

        Map<String, Object> response = new HashMap<>();

        List<SoftSkillInfoMapping> mappings = softSkillsRepository.getInfo();
        if (mappings != null && mappings.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYYMMDD);
            SoftSkillInfoMapping m = mappings.get(mappings.size() - 1);
            response.put("softSkill", new SoftSkillInfoResponse(m.getId(), CommonConstant.NUMBER_1, m.getSoftSkillName(), m.getNumberRoom(), m.getCountTaskCompleted(), m.getStatus(), sdf.format(m.getCreatedDate()), m.getCreatedName()));
            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.ADD_SOFT_SKILL);
        }
        return response;
    }

    @Override
    public Map<String, Object> updateSoftSkill(SoftSkillRequest softSkillRequest) {
        SoftSkills softSkills =
                softSkillsRepository.findById(softSkillRequest.getSoftSkillId())
                        .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        Map<String, Object> response = new HashMap<>();

        softSkills.setName(softSkillRequest.getSoftSkillName() == null ? softSkills.getName() : softSkillRequest.getSoftSkillName());
        if ("Dừng".equals(softSkillRequest.getStatus())) {
            softSkills.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
        } else if ("Hoạt động".equals(softSkillRequest.getStatus())) {
            softSkills.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        }
        softSkills.setCommonUpdate(commonService.idUserAccountLogin());
        softSkillsRepository.save(softSkills);

        response.put("SoftSkillUpdate", softSkills);
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.UPDATE_SOFT_SKILL);
        return response;
    }

    @Override
    public Map<String, Object> deleteSoftSkill(Long[] ids) {
        Map<String, Object> response = new HashMap<>();

        for (Long id : ids) {
            SoftSkills softSkills =
                    softSkillsRepository.findById(id)
                            .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

            softSkills.setCommonDelete(commonService.idUserAccountLogin());
            softSkillsRepository.save(softSkills);

            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.DELETE_SOFT_SKILL);
        }
        return response;
    }
}

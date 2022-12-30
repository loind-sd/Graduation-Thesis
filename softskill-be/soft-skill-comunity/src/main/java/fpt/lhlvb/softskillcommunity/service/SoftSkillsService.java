package fpt.lhlvb.softskillcommunity.service;
import fpt.lhlvb.softskillcommunity.entity.SoftSkills;
import fpt.lhlvb.softskillcommunity.model.request.user.SoftSkillRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.ComboboxResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface SoftSkillsService {
    List<SoftSkills> getAllSoftSkill();

    List<ComboboxResponse> getSoftSkills();

    ApiResponse getSoftSkillInfo();

    Map<String, Object> addSoftSkill(SoftSkillRequest softSkillRequest);

    Map<String, Object> updateSoftSkill(SoftSkillRequest softSkillRequest);

    Map<String, Object> deleteSoftSkill(Long[] ids);
}

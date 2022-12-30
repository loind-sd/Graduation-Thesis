package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.entity.SoftSkills;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.SoftSkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(URLConstant.SOFT_SKILL_URL)
public class SoftSkillController {

    @Autowired
    private SoftSkillsService softSkillsService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getListSoftSkill() {
        List<SoftSkills> response = softSkillsService.getAllSoftSkill();
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
        return ResponseEntity.ok().body(apiResponse);
    }
}

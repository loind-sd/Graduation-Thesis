package fpt.lhlvb.softskillcommunity.controller.admin;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.entity.SoftSkills;
import fpt.lhlvb.softskillcommunity.model.request.user.SoftSkillRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.SoftSkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URLConstant.SOFT_SKILL_ADMIN_URL)
public class SoftSkillAdminController {
    @Autowired
    private SoftSkillsService softSkillsService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getListSoftSkill() {
        return ResponseEntity.ok().body(softSkillsService.getSoftSkillInfo());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addSoftSkill(@RequestBody SoftSkillRequest softSkillRequest){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, softSkillsService.addSoftSkill(softSkillRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateSoftSkill(@RequestBody SoftSkillRequest softSkillRequest){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, softSkillsService.updateSoftSkill(softSkillRequest));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse> deleteSoftSkill(@RequestBody Long[] ids){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS, softSkillsService.deleteSoftSkill(ids));
        return ResponseEntity.ok().body(apiResponse);
    }
}

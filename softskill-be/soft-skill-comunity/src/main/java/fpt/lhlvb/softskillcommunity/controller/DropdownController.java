package fpt.lhlvb.softskillcommunity.controller;

import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.TaskRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(URLConstant.DROP_DOWN_URL)
public class DropdownController {
    @Autowired
    private TaskRelationService taskRelationService;

    @GetMapping("/taskBySoftSkillId/{id}")
    public ResponseEntity<ApiResponse> getTaskBySoftSkillId(@PathVariable Long id) {
        return ResponseEntity.ok().body(taskRelationService.getTaskBySoftSkillId(id));
    }

    @GetMapping("/recommendTaskType")
    public ResponseEntity<ApiResponse> getRecommendTaskType() {
        return ResponseEntity.ok().body(taskRelationService.getRecommendTaskType());
    }
}

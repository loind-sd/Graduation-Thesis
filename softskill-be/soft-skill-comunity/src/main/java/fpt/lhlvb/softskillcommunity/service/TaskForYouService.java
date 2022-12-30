package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface TaskForYouService {
    ApiResponse getTaskForYou();
    ApiResponse addTaskForYou(Map<String, Object> req);
    ApiResponse updateTaskForYou(Map<String, Object> req);
    ApiResponse deleteTaskForYou(Map<String, Object> req);

}

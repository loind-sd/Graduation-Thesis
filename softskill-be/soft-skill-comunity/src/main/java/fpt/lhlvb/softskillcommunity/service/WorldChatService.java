package fpt.lhlvb.softskillcommunity.service;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.WorldChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface WorldChatService {
    ApiResponse loadMessage(Map<String, Object> req);

    ApiResponse addMessage(Map<String, Object> req);

    ApiResponse updateMessage(Map<String, Object> req);

    ApiResponse deleteMessage(Long id);
}

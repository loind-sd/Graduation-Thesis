package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.entity.Notification;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface NotificationService {
    ApiResponse getNotifications(int page);

    Map<String, Object> addNotification(Map<String, Object> req);

    Map<String, Object> updateNotificationStatus(Long id);

    Map<String, Object> updateNotificationStatus(Notification notification);

    ApiResponse deleteNotification(Long id);

    Flux<ServerSentEvent<List<Notification>>> getNotificationsByUserToID(Long id);
}

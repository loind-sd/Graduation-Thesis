package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.entity.Notification;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping(URLConstant.NOTIFICATION_URL)
@Slf4j
@Transactional
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getNotifications(@RequestParam int page) {
        return ResponseEntity.ok().body(notificationService.getNotifications(page));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addNotification(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, notificationService.addNotification(req)));
    }

    @GetMapping(value = "/show/{id}", produces = "text/event-stream;charset=UTF-8")
    public Flux<ServerSentEvent<List<Notification>>> streamLastMessage(@PathVariable Long id) {
        return notificationService.getNotificationsByUserToID(id);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteNotification(@RequestParam Long id) {
        return ResponseEntity.ok().body(notificationService.deleteNotification(id));
    }
}


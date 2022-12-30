package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.ChatChannel;
import fpt.lhlvb.softskillcommunity.entity.ChatChannelInfo;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.ContactResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.FriendResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.ChatChannelMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import fpt.lhlvb.softskillcommunity.repository.ChatChannelInfoRepository;
import fpt.lhlvb.softskillcommunity.repository.ChatChannelRepository;
import fpt.lhlvb.softskillcommunity.service.ChatChannelService;
import fpt.lhlvb.softskillcommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatChannelServiceImpl implements ChatChannelService {

    @Autowired
    private ChatChannelRepository chatChannelRepository;

    @Autowired
    private ChatChannelInfoRepository chatChannelInfoRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommonService commonService;

    @Override
    public ApiResponse getAllContact() {
        Long id = commonService.idUserAccountLogin();
        List<ChatChannelMapping> chatChannels = chatChannelRepository.getAllContact(id);
        List<ContactResponse> responses = new ArrayList<>();
        for (ChatChannelMapping c : chatChannels) {
            responses.add(new ContactResponse(c.getId(), c.getName(), c.getRoomCode(), c.getIsMaster() == null ? CommonConstant.DELETE_FLAG_FALSE : c.getIsMaster(), CommonConstant.DELETE_FLAG_FALSE));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, responses);
    }

    @Override
    public ApiResponse addNewContact(Map<String, Object> req) {
        ChatChannel chatChannel = new ChatChannel();
        String roomCode = randomCode();
        Long id = null;
        Optional<ChatChannel> optional = chatChannelRepository.findByRoomCode(roomCode);
        if (optional.isEmpty()) {
            chatChannel.setName(String.valueOf(req.get("name")));
            chatChannel.setRoomCode(roomCode);
            chatChannel.setCommonRegister(commonService.idUserAccountLogin());
            id = chatChannelRepository.save(chatChannel).getId();

            List<Integer> list = (List<Integer>) req.get("member");
            for (Integer l : list) {
                ChatChannelInfo info = new ChatChannelInfo();
                info.setUserId(Long.valueOf(l));
                info.setChatChannelId(id);
                info.setIsMaster(CommonConstant.DELETE_FLAG_FALSE);
                info.setCommonRegister(commonService.idUserAccountLogin());
                chatChannelInfoRepository.save(info);

                // send Notification for them
                Map<String, Object> notificationRequest = new HashMap<>();
                notificationRequest.put("senderId", commonService.idUserAccountLogin());
                notificationRequest.put("typeId", CommonConstant.NOTIFICATION_ADD_NEW_CONTACT);
                notificationRequest.put("roomName", String.valueOf(req.get("name")));
                notificationRequest.put("userId", Long.valueOf(l));
                notificationService.addNotification(notificationRequest);
            }

            ChatChannelInfo info = new ChatChannelInfo();
            info.setUserId(commonService.idUserAccountLogin());
            info.setChatChannelId(id);
            info.setIsMaster(CommonConstant.DELETE_FLAG_TRUE);
            info.setCommonRegister(commonService.idUserAccountLogin());
            chatChannelInfoRepository.save(info);
        }

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, new ContactResponse(id, String.valueOf(req.get("name")), roomCode, CommonConstant.DELETE_FLAG_FALSE, CommonConstant.DELETE_FLAG_FALSE));
    }

    @Override
    public ApiResponse getFriendToAdd(Map<String, Object> req) {
        List<FriendMapping> mappings = chatChannelInfoRepository.getFriendToAdd(commonService.idUserAccountLogin(), Long.parseLong(String.valueOf(req.get("roomId"))));
        List<FriendResponse> response = new ArrayList<>();

        for (FriendMapping u : mappings) {
            response.add(new FriendResponse(u.getId(), u.getName(), u.getNickName(), u.getPicture()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS,response);
    }

    @Override
    public ApiResponse addMember(Map<String, Object> req) {
        Long id = Long.parseLong(String.valueOf(req.get("roomId")));
        List<Integer> list = (List<Integer>) req.get("member");
        for (Integer l : list) {
            ChatChannelInfo info = new ChatChannelInfo();
            info.setUserId(Long.valueOf(l));
            info.setChatChannelId(id);
            info.setIsMaster(CommonConstant.DELETE_FLAG_FALSE);
            info.setCommonRegister(commonService.idUserAccountLogin());
            chatChannelInfoRepository.save(info);

            // send Notification for them
            Map<String, Object> notificationRequest = new HashMap<>();
            notificationRequest.put("senderId", commonService.idUserAccountLogin());
            notificationRequest.put("typeId", CommonConstant.NOTIFICATION_ADD_NEW_CONTACT);
            notificationRequest.put("roomName", String.valueOf(req.get("name")));
            notificationRequest.put("userId", Long.valueOf(l));
            notificationService.addNotification(notificationRequest);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse getAllMemberInRoom(Map<String, Object> req) {
        List<FriendMapping> mappings = chatChannelInfoRepository.getAllMemberInRoom(commonService.idUserAccountLogin(), Long.parseLong(String.valueOf(req.get("roomId"))));
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, mappings);
    }

    @Override
    public ApiResponse kickUser(Map<String, Object> req) {
        Optional<ChatChannelInfo> info = chatChannelInfoRepository.findByChatChannelIdAndUserIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("roomId"))), Long.parseLong(String.valueOf(req.get("userId"))), CommonConstant.DELETE_FLAG_FALSE);
        if (info.isPresent()) {
            info.get().setCommonDelete(commonService.idUserAccountLogin());
            chatChannelInfoRepository.save(info.get());
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    private String randomCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

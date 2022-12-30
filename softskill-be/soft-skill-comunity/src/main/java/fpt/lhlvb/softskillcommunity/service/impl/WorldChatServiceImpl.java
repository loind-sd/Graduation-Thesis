package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.entity.WorldChat;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.WorldChatResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.WorldChatMapping;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.repository.WorldChatRepository;
import fpt.lhlvb.softskillcommunity.service.WorldChatService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class WorldChatServiceImpl implements WorldChatService {

    @Autowired
    private WorldChatRepository worldChatRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommonService commonService;
    @Override
    public ApiResponse loadMessage(Map<String, Object> req) {
        Long msgId = req.get("msgId") == null ? Long.MAX_VALUE : Long.parseLong(String.valueOf(req.get("msgId")));
        int offset = req.get("offset") == null ? 0 : Integer.parseInt(String.valueOf(req.get("offset")));
        List<WorldChatMapping> worldChats = worldChatRepository.getWithLimit(Integer.parseInt(String.valueOf(req.get("limit"))), CommonConstant.DELETE_FLAG_FALSE, commonService.idUserAccountLogin(), msgId, offset, Long.parseLong(String.valueOf(req.get("channelId"))));
        List<WorldChatResponse> worldChatResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        Timestamp now = CommonUtils.resultTimestamp();
        for (WorldChatMapping w : worldChats) {
            worldChatResponses.add(new WorldChatResponse(
                    w.getWorldChatId(),
                    w.getWorldChatContent(),
                    w.getCreatedBy(),
                    w.getCreatedName(),
                    w.getCreatedMail(),
                    w.getCreatedNickName(),
                    w.getCreatedPicture(),
                    CommonUtils.calculateTimeStamp(now, w.getCreatedTime()),
                    w.getStatus(),
                    w.getIsFix()));
        }

        response.put("data", worldChatResponses);
        response.put("loadType", msgId.equals(Long.MAX_VALUE) ? "loadFirst" : "loadMore");

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse addMessage(Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        WorldChat worldChat = new WorldChat();
        Long channelId = Long.parseLong(String.valueOf(req.get("channelId")));
        worldChat.setChannelId(channelId);
        if (channelId.equals(2L)) {
            if (!worldChatRepository.checkExistInvitation(commonService.idUserAccountLogin(), Long.parseLong(String.valueOf(req.get("content")))).get()) {
                String[] initInvite = new String[]{"Nếu bạn chưa có nhóm để luyện tập, hãy tham gia cùng mình nhé ", "Chúng mình đang chờ bạn nè, tham gia ngay bạn ơi ", "Phòng chúng mình chỉ thiếu 1 thành viên nữa thôi", "Cùng mình phá đảo hết các nhiệm vụ nào bạn ơi", "Chúng mình chỉ chờ mỗi bạn thôi đó"};
                int rnd = new Random().nextInt(initInvite.length);
                worldChat.setContent(initInvite[rnd] + " | " + req.get("content"));
            } else {
                response.put("info", "already sent invite today");
                return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
            }
        } else {
            worldChat.setContent(String.valueOf(req.get("content")));
        }
        worldChat.setCommonRegister(commonService.idUserAccountLogin());

        Timestamp now = CommonUtils.resultTimestamp();
        WorldChat chat = worldChatRepository.save(worldChat);
        Users users = usersRepository.findByIdAndDeleteFlag(chat.getCreatedBy(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        WorldChatResponse worldChatResponse = new WorldChatResponse(
                chat.getId(),
                chat.getContent(),
                chat.getCreatedBy(),
                users.getName(),
                users.getMail(), users.getNickname(), users.getPicture(), CommonUtils.calculateTimeStamp(now, chat.getCreatedAt()), "isFriend", ""
        );

        response.put("chat", worldChatResponse);
        response.put("add", "done");

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);

    }

    @Override
    public ApiResponse updateMessage(Map<String, Object> req) {
        WorldChat worldChat = worldChatRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("id"))), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        worldChat.setContent(String.valueOf(req.get("content")));
        worldChat.setCommonUpdate(commonService.idUserAccountLogin());
        worldChatRepository.save(worldChat);

        WorldChatResponse worldChatResponse = new WorldChatResponse();
        worldChatResponse.setId(worldChat.getId());
        worldChatResponse.setContent(worldChat.getContent());
        worldChatResponse.setIsFix(" (đã chỉnh sửa)");
        worldChatResponse.setCreatedBy(worldChat.getCreatedBy());
        Users users = usersRepository.findByIdAndDeleteFlag(worldChat.getCreatedBy(), CommonConstant.DELETE_FLAG_FALSE).get();
        worldChatResponse.setCreatedName(users.getName());
        worldChatResponse.setCreatedMail(users.getMail());
        worldChatResponse.setCreatedNickName(users.getNickname());
        worldChatResponse.setCreatedPicture(users.getPicture());
        worldChatResponse.setCreatedTime(CommonUtils.calculateTimeStamp(CommonUtils.resultTimestamp(), worldChat.getCreatedAt()));
        worldChatResponse.setStatus("nothing");

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, worldChatResponse);
    }

    @Override
    public ApiResponse deleteMessage(Long id) {
        WorldChat worldChat = worldChatRepository.findByIdAndDeleteFlag(id, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        worldChat.setCommonDelete(commonService.idUserAccountLogin());
        Map<String, Object> response = new HashMap<>();
        response.put("deleteMessage", "done");
        response.put("id", worldChatRepository.save(worldChat));

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }
}

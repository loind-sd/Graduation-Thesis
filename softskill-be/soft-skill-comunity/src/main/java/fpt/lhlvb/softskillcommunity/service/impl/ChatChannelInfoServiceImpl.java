package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.ChatChannelInfo;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.repository.ChatChannelInfoRepository;
import fpt.lhlvb.softskillcommunity.service.ChatChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ChatChannelInfoServiceImpl implements ChatChannelInfoService {

    @Autowired
    private ChatChannelInfoRepository chatChannelInfoRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public ApiResponse outRoom(Map<String, Object> req) {
        Optional<ChatChannelInfo> info = chatChannelInfoRepository.findByChatChannelIdAndDeleteFlagAndUserId(Long.parseLong(String.valueOf(req.get("chatChannelId"))), CommonConstant.DELETE_FLAG_FALSE, commonService.idUserAccountLogin());
        if (info.isPresent()) {
            ChatChannelInfo channelInfo = info.get();
            channelInfo.setCommonDelete(commonService.idUserAccountLogin());
            chatChannelInfoRepository.save(channelInfo);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS, null);
    }
}
